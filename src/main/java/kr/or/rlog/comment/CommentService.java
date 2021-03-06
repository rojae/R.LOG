package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.Status;
import kr.or.rlog.likey.CommentLikesRepository;
import kr.or.rlog.likey.LikesType;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikesRepository commentLikesRepository;

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
            comment.setLikeCount(0L);
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
            comment.setLikeCount(0L);
            savedPost.get().addComment(comment);
        }
        commentRepository.save(comment);
        return true;
    }

    public CommentDto createRoot(Long postId, Account user) {
        Map<Long, List<CommentDto>> groupingByParent = commentRepository.findAllByPostAndStatusNotOrderByCreatedDateDesc(new Post(postId), Status.UNABLE)
                .stream()
                .map(ce -> {
                    if (user != null && user.getRole().equals("ADMIN")) {
                        return new CommentDto(ce.getId(), ce.getContent()
                                , new AccountDto(ce.getWriter().getId(), ce.getWriter().getEmail(), ce.getWriter().getUserName(), ce.getWriter().getProfileImage())
                                , user, ce.getModifiedDate(), ce.getLikeCount(), commentLikesRepository.existsCommentLikesByAccountAndCommentAndStatus(user, new Comment(ce.getId()), LikesType.ENABLE), ce.getParentId(), ce.getStatus());
                    }
                    else if (ce.getStatus().equals(Status.SECRET) && (user == null || !ce.getWriter().getId().equals(user.getId()))) {
                        return new CommentDto(ce.getId(), "비밀댓글입니다"
                                , new AccountDto("", "???", "/assets/img/illustrations/profiles/question-mark.png")
                                , ce.getModifiedDate(), ce.getLikeCount(), commentLikesRepository.existsCommentLikesByAccountAndCommentAndStatus(user, new Comment(ce.getId()), LikesType.ENABLE), ce.getParentId(), ce.getStatus());
                    }
                    else {
                        return new CommentDto(ce.getId(), ce.getContent()
                                , new AccountDto(ce.getWriter().getId(), ce.getWriter().getEmail(), ce.getWriter().getUserName(), ce.getWriter().getProfileImage())
                                , user, ce.getModifiedDate(), ce.getLikeCount(), commentLikesRepository.existsCommentLikesByAccountAndCommentAndStatus(user, new Comment(ce.getId()), LikesType.ENABLE), ce.getParentId(), ce.getStatus());
                    }
                })
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

    public Optional<Comment> getOne(Long commentId) {
        return commentRepository.findById(commentId);
    }


    @Transactional
    public boolean editProc(Long id, Comment comment) {
        Optional<Comment> savedComment = commentRepository.findById(id);
        if (savedComment.isPresent()) {
            savedComment.get().setContent(comment.getContent());
            savedComment.get().setStatus(comment.getStatus());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteProc(Long id) {
        Optional<Comment> savedComment = commentRepository.findById(id);
        if (savedComment.isPresent()) {
            savedComment.get().setStatus(Status.UNABLE);
            return true;
        } else {
            return false;
        }
    }

    public Page<CommentDto> getPage(Pageable pageable, Account user) {
        Page<Comment> pages = commentRepository.findAllByWriterAndStatusNot(pageable, user, Status.UNABLE);
        return pages.map(CommentDto::of);
    }
}
