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
    public Post createNew(Post post, Account user) {
        Optional<Category> category = categoryRepository.findById(post.getCategory().getId());
        if (category.isPresent()) {
            category.get().addPost(post);
            post.setWriter(user);
            user.addPost(post);
            return postRepository.save(post);
        }
        return null;
    }

    public List<Post> postAll() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    @Transactional
    public Page<PostDto> getPage(Pageable pageable) {
        Page<Post> pages = postRepository.findAllByOrderByCreatedDateDesc(pageable);
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
                    )
            );
            target.setUrl("/post/"+origin.getId());
            list.add(target);
        }
        return new PageImpl<PostDto>(list);
    }
}
