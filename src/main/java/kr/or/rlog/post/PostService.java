package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public Optional<Post> getPost(Long pId){
        return postRepository.findById(pId);
    }

    @Transactional
    public Post createNew(Post post, Account user){
        Optional<Category> category = categoryRepository.findById(post.getCategory().getId());
        if(category.isPresent()){
            category.get().addPost(post);
            post.setWriter(user);
            user.addPost(post);
            return postRepository.save(post);
        }
        return null;
    }

    public List<Post> postAll(){
        return postRepository.findAllByOrderByCreatedDateDesc();
    }
}
