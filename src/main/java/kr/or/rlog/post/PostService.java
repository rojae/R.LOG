package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.category.CategoryRepository;
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
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public Optional<Post> getPost(Long pId) {
        return postRepository.findById(pId);
    }

    @Transactional
    public void deletePost(Long pId, Account user){
        Optional<Post> post = postRepository.findById(pId);
        if(post.isPresent()) {
            postRepository.deleteById(pId);
            user.deletePost(post.get());
        }
    }

    @Transactional
    public Post createNew(Post post, Account user) {
        Optional<Category> category = categoryRepository.findById(post.getCategory().getId());
        if (category.isPresent()) {
            category.get().addPost(post);
            user.addPost(post);
            post.setCategory(category.get());
            post.setWriter(user);
            return postRepository.save(post);
        }
        return null;
    }

    public List<Post> postAll() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    @Transactional
    public Page<PostDto> getPage(Pageable pageable, String keyword) {
        Page<Post> pages;

        if(keyword.equals(""))
            pages = postRepository.findAllByOrderByCreatedDateDesc(pageable);
        else
            pages = postRepository.findAllByTitleContainsIgnoreCaseOrderByCreatedDateDesc(pageable, keyword);

        List<PostDto> list = new ArrayList<>();
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
                    new AccountDto(origin.getWriter().getEmail()
                            ,origin.getWriter().getUserName()
                            ,origin.getWriter().getProfileImage()
                    )
            );
            target.setUrl("/post/"+origin.getId());
            list.add(target);
        }
        return new PageImpl<PostDto>(list, pages.getPageable(), pages.getTotalElements());
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
    }

}
