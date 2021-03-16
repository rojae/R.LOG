package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Transactional
    public boolean createNew(Comment comment, Account user, Long postId) {
        Optional<Post> savedPost = postRepository.findById(postId);
        if (!savedPost.isPresent())
            return false;
        else {
            comment.setWriter(user);
            comment.setPost(savedPost.get());
            savedPost.get().addComment(comment);
        }
        commentRepository.save(comment);
        return true;
    }

    @Transactional
    public boolean createNew(Comment comment, Account user, Long postId, Long parentId) {
        Optional<Post> savedPost = postRepository.findById(postId);
        Optional<Comment> savedComment = commentRepository.findById(parentId);

        if (!savedPost.isPresent() || !savedComment.isPresent())
            return false;
        else {
            comment.setWriter(user);
            comment.setPost(savedPost.get());
            comment.setParentId(parentId);
            savedPost.get().addComment(comment);
        }
        commentRepository.save(comment);
        return true;
    }

    public List<Comment> getComment(Post post) {
        return commentRepository.findByPostOrderByCreatedDateDesc(post);
    }

}
