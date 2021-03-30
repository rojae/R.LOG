package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

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
            comment.setParentId(0L);
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
            comment.setStatus(Status.ENABLE);
            savedPost.get().addComment(comment);
        }
        commentRepository.save(comment);
        return true;
    }

    public CommentDto createRoot(Long postId, Account user) {
        Map<Long, List<CommentDto>> groupingByParent = commentRepository.findAllByStatus(Status.ENABLE)
                .stream()
                .filter(comment -> comment.getPost().getId().equals(postId))
                .map(ce -> new CommentDto(ce.getId(), ce.getContent()
                        , new AccountDto(ce.getWriter().getId(), ce.getWriter().getEmail(), ce.getWriter().getUserName(), ce.getWriter().getProfileImage())
                        , user, ce.getModifiedDate(), ce.getParentId())
                )
                .collect(groupingBy(CommentDto::getParentId));


        CommentDto rootCommentDto = new CommentDto(0L, "ROOT", null, null, null);
        addSubComment(rootCommentDto, groupingByParent);

        return rootCommentDto;
    }

    private void addSubComment(CommentDto parent, Map<Long, List<CommentDto>> groupingByParentId) {
        List<CommentDto> subComments = groupingByParentId.get(parent.getCommentId());

        if (subComments == null)
            return;

        parent.setSubComments(subComments);

        subComments
                .forEach(s -> {
                    addSubComment(s, groupingByParentId);
                });
    }

    public Optional<Comment> getOne(Long commentId){
        return commentRepository.findById(commentId);
    }


    @Transactional
    public boolean editProc(Long id, String content) {
        Optional<Comment> savedComment = commentRepository.findById(id);
        if(savedComment.isPresent()) {
            savedComment.get().setContent(content);
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    public boolean deleteProc(Long id) {
        Optional<Comment> savedComment = commentRepository.findById(id);
        if(savedComment.isPresent()) {
            savedComment.get().setStatus(Status.UNABLE);
            return true;
        }else{
            return false;
        }
    }
}
