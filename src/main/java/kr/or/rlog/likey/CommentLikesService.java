package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.comment.CommentRepository;
import kr.or.rlog.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CommentLikesService {

    @Autowired
    private CommentLikesRepository commentLikesRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Transactional
    public boolean createNew(Account user, Long commentId) {
        Optional<Comment> savedComment = commentRepository.findById(commentId);

        if (savedComment.isPresent()) {
            CommentLikes savedPostLikes = commentLikesRepository.save(new CommentLikes(user, savedComment.get()));
            savedComment.get().addCommentLike(savedPostLikes);
            savedComment.get().setLikeCount(savedComment.get().plusLikeCount());
            return true;
        } else {
            return false;
        }
    }

    /*
     * proc1 : 좋아요 -> 좋아요 취소
     * proc2 : 좋아요 취소 -> 좋아요
     * done : 현재로써는 좋아요 > 좋아요 취소만 사용
     */
    @Transactional
    public boolean editProc(Account user, Long commentId) {
        Optional<CommentLikes> savedLike = Optional.ofNullable(commentLikesRepository.findByAccountAndCommentAndStatus(user, new Comment(commentId), LikesType.ENABLE));
        Optional<Comment> savedComment = commentRepository.findById(commentId);

        if (!savedLike.isPresent() || !savedComment.isPresent())
            return false;
        else if (LikesType.isEnable(savedLike.get().getStatus())) {
            savedLike.get().setStatus(LikesType.UNABLE);
            savedComment.get().setLikeCount(savedComment.get().minusLikeCount());
        }
        else if (LikesType.isUnable(savedLike.get().getStatus())) {
            savedLike.get().setStatus(LikesType.ENABLE);
            savedComment.get().setLikeCount(savedComment.get().plusLikeCount());
        }

        return true;
    }


}
