package kr.or.rlog.post;

import com.querydsl.core.QueryResults;
import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.account.AccountRepository;
import kr.or.rlog.account.AccountService;
import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.category.CategoryRepository;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.comment.CommentRepository;
import kr.or.rlog.common.Status;
import kr.or.rlog.likey.LikesType;
import kr.or.rlog.likey.PostLikes;
import kr.or.rlog.likey.PostLikesRepository;
import kr.or.rlog.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostLikesRepository postLikesRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CommentRepository commentRepository;

    public Optional<Post> getPost(Long pId) {
        return postRepository.findById(pId);
    }

    /** ==================================================================
     * @methodName : getPost
     * @description : 단일 페이지 내용 조회
     *         - 관리자의 경우 -> 삭제되지 않은 글 전체
     *         - 사용자의 경우 -> 활성화된 글만 조회
     *         QueryDSL로 최적화를 어떻게 하지...
     * @func1 : 게시글 관련 정보 가져오기.
     * @func2 : 게시글 좋아요. 정보 가져오기.
     * @func3 : 사용자의 게시글 좋아요. 활성화 여부 가져오기.
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/
    public PostDetailDto getPostDetail(Long pId) {
        PostDetailDto postDetail = postRepository.getPostDetail(pId);
        postDetail.setPostLikes(postLikesRepository.isLike(pId));
        postDetail.setLikesCount(postLikesRepository.getLikeCount(pId));
        return postDetail;
    }

    @Transactional
    public boolean deletePost(Long pId, Account user) {
        Optional<Post> post = postRepository.findById(pId);
        List<Comment> commments = commentRepository.findAllByPost(new Post(pId));

        if (post.isPresent() && accountService.findMe(user).postIsMine(post.get())) {
            post.get().setStatus(Status.UNABLE);

            // 해당 글의 댓글도 비활성화로 전환
            for (Comment i : commments) {
                i.setStatus(Status.UNABLE);
            }

            return true;
        }
        return false;
    }

    @Transactional
    public Post createNew(Post post, Account user) {
        Optional<Category> category = categoryRepository.findById(post.getCategory().getId());
        Account account = accountRepository.findAccountByEmail(user.getEmail());

        if (category.isPresent()) {
            category.get().addPost(post);
            account.addPost(post);
            post.setCategory(category.get());
            post.setWriter(account);
            post.setStatus(post.getStatus());
            return postRepository.save(post);
        }
        return null;
    }

    /*
        관리자의 경우 -> 삭제되지 않은 글 전체
        사용자의 경우 -> 활성화된 글만 조회
    */
    @Transactional
    public Page<PostDto> getPage(Pageable pageable, String keyword, Account user) {
        log.info("PostService :: getPage called");
        Page<PostDto> postPage = postRepository.findPostPage(pageable, keyword, user);
        log.info("PostService :: getPage end");

        return postPage;
    }

    /*
        관리자의 경우 -> 삭제되지 않은 글 전체
        사용자의 경우 -> 활성화된 글만 조회
    */
    @Transactional
    public Page<PostDto> getPageByCategory(Pageable pageable, String keyword, Account user, Long categoryId) {
        Page<Post> pages;
        List<PostDto> list = new ArrayList<>();

        if (user != null && user.getRole().equals("ADMIN")) {
            if (keyword.equals(""))
                pages = postRepository.findAllByStatusNotAndCategoryOrderByCreatedDateDesc(pageable, Status.UNABLE, new Category(categoryId));
            else
                pages = postRepository.findAllByTitleContainsIgnoreCaseAndStatusNotAndCategoryOrderByCreatedDateDesc(pageable, keyword, Status.UNABLE, new Category(categoryId));
        } else {
            if (keyword.equals(""))
                pages = postRepository.findAllByStatusAndCategoryOrderByCreatedDateDesc(pageable, Status.ENABLE, new Category(categoryId));
            else
                pages = postRepository.findAllByTitleContainsIgnoreCaseAndStatusAndCategoryOrderByCreatedDateDesc(pageable, keyword, Status.ENABLE, new Category(categoryId));
        }

        for (Post origin : pages) {
            PostDto target = new PostDto();
            BeanUtils.copyProperties(origin, target);
            target.setCategory(
                    new CategoryDto(origin.getCategory().getId()
                            , origin.getCategory().getCategoryName()
                            , origin.getCategory().getParentId()
                    )
            );
            target.setWriter(
                    new AccountDto(
                            origin.getWriter().getId()
                            , origin.getWriter().getEmail()
                            , origin.getWriter().getUserName()
                            , origin.getWriter().getProfileImage()
                    )
            );
            target.setModifiedDate(TimeUtils.dateTimeToYYYYMMDD(origin.getModifiedDate()));
            target.setCreatedDate(TimeUtils.dateTimeToYYYYMMDD(origin.getCreatedDate()));
            target.setUrl("/post/" + origin.getId());
            target.setStatus(origin.getStatus());

            list.add(target);
        }
        return new PageImpl<PostDto>(list, pages.getPageable(), pages.getTotalElements());
    }

    /**
     * @methodName: getTopPosts
     * @author: rojae
     * @date: 2021-08-15
     * @Description: 상위 인기 글을 조회하는 기능.
     * 좋아요 테이블을 크게 돌기 때문에, 어플리케이션 메모리에서 list로 넘겨주어, sql in으로 조회
     * @Bug: QueryDSL 버그로 인한 subQuery limit 미동작.
     */
    @Transactional
    public List<PostDto> getTopPosts(int popularSize) {
        log.info("PostService :: getTopPosts called");
        List<PostDto> topPosts = postLikesRepository.findByPostIds(popularSize);
        log.info("PostService :: getTopPosts end");

        return topPosts;
    }


    /*
        ~ 게시글 수정 저장 부분
        AccountService에서의 메일인증 이후, 권한 변경에 이어서
        게시글 수정 이후에, update 쿼리를 위해서 JPA 종속성을 사용
     */
    @Transactional
    public void editSave(Post newPost) {
        Optional<Post> savedPost = postRepository.findById(newPost.getId());
        savedPost.get().setCategory(newPost.getCategory());
        savedPost.get().setTitle(newPost.getTitle());
        savedPost.get().setHeader(newPost.getHeader());
        savedPost.get().setContent(newPost.getContent());
        savedPost.get().setStatus(newPost.getStatus());
        savedPost.get().setThumbNail(newPost.getThumbNail());
    }

}
