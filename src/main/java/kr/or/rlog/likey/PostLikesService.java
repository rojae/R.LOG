package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PostLikesService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikesRepository postLikesRepository;

    @Transactional
    public boolean createNew(Account user, Long postId){
        Optional<Post> savedPost = postRepository.findById(postId);

        if(savedPost.isPresent()) {
            PostLikes savedPostLikes = postLikesRepository.save(new PostLikes(user, savedPost.get()));
            savedPost.get().addPostLike(savedPostLikes);
            return true;
        }
        else {
            return false;
        }
    }

}
