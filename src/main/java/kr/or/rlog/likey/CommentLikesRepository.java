package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long>{

    CommentLikes findByAccountAndCommentAndStatus(Account account, Comment comment, LikesType likesType);
    boolean existsCommentLikesByAccountAndCommentAndStatus(Account account, Comment comment, LikesType likesType);

}
