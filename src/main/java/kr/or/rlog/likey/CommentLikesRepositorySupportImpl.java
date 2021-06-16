package kr.or.rlog.likey;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
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
        return query.selectFrom(commentLikes)
                .where(
                        eqAccount(account),
                        commentLikes.comment.eq(comment),
                        commentLikes.status.eq(likesType)
                ).fetchOne();
    }

    @Override
    public boolean existsCommentLikesByAccountAndCommentAndStatus(Account account, Comment comment, LikesType likesType) {
        Integer result = query.selectOne()
                .from(commentLikes)
                .where(
                        eqAccount(account),
                        commentLikes.comment.eq(comment),
                        commentLikes.status.eq(likesType)
                ).fetchOne();

        return result != null;
    }

    private BooleanExpression eqAccount(Account account) {
        if (account == null) {
            return null;
        }

        return commentLikes.account.eq(account);
    }

}
