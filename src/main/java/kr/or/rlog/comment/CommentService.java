package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Transactional
    public boolean createNew(Comment comment, Account user, Long postId){
        Optional<Post> savedPost = postRepository.findById(postId);
        if(!savedPost.isPresent())
            return false;
        else{
            savedPost.get().addComment(comment);
            comment.setWriter(user);
            comment.setPost(savedPost.get());
        }
        commentRepository.save(comment);
        return true;
    }

    public List<Comment> getComment(Post post){
        return commentRepository.findByPostOrderByCreatedDateDesc(post);
    }

}
