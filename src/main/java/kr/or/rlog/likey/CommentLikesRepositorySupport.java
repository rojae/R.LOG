package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.comment.Comment;

public interface CommentLikesRepositorySupport {
    CommentLikes findByAccountAndCommentAndStatus(Account account, Comment comment, LikesType likesType);
    boolean existsCommentLikesByAccountAndCommentAndStatus(Account account, Comment comment, LikesType likesType);

}
