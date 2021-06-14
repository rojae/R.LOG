package kr.or.rlog.likey;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;

import static kr.or.rlog.likey.QCommentLikes.commentLikes;

public class CommentLikesRepositorySupportImpl implements CommentLikesRepositorySupport {

    @Autowired
    JPAQueryFactory query;

    @Override
    public CommentLikes findByAccountAndCommentAndStatus(Account account, Comment comment, LikesType likesType) {
        if (account == null) {
            return query.selectFrom(commentLikes)
                    .where(
                            commentLikes.comment.eq(comment)
                                    .and(commentLikes.status.eq(likesType))
                    ).fetchOne();
        } else {
            return query.selectFrom(commentLikes)
                    .where(
                            commentLikes.account.eq(account)
                                    .and(commentLikes.comment.eq(comment))
                                    .and(commentLikes.status.eq(likesType))
                    ).fetchOne();
        }
    }

    @Override
    public boolean existsCommentLikesByAccountAndCommentAndStatus(Account account, Comment comment, LikesType likesType) {
        Integer result = null;

        if(account == null) {
            result = query.selectOne()
                    .from(commentLikes)
                    .where(
                            commentLikes.comment.eq(comment)
                    ).fetchOne();
        }else{
            result = query.selectOne()
                    .from(commentLikes)
                    .where(
                            commentLikes.account.eq(account)
                            .and(commentLikes.comment.eq(comment))
                    ).fetchOne();
        }

        return result != null;
    }
}
