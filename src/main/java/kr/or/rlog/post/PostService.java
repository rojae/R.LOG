package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public Optional<Post> getPost(Long pId){
        return postRepository.findById(pId);
    }

    @Transactional
    public Post createNew(Post post, Account user){
        post.setWriter(user);
        user.addPost(post);
        return postRepository.save(post);
    }

    public List<Post> postAll(){
        return postRepository.findAllByOrderByCreatedDateDesc();
    }
}
