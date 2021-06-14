package kr.or.rlog.likey;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.post.Post;
import org.springframework.beans.factory.annotation.Autowired;

import static kr.or.rlog.likey.QPostLikes.postLikes;

public class PostLikesRepositorySupportImpl implements PostLikesRepositorySupport {

    @Autowired
    JPAQueryFactory query;

    @Override
    public boolean existsByAccountAndPostAndStatus(Account account, Post post, LikesType status) {
        Integer result = null;

        if (account == null) {
            result = query.selectOne()
                    .from(postLikes)
                    .where(
                            postLikes.post.eq(post)
                                    .and(postLikes.status.eq(status))
                    ).fetchOne();
        } else {
            result = query.selectOne()
                    .from(postLikes)
                    .where(
                            postLikes.account.eq(account)
                                    .and(postLikes.post.eq(post))
                                    .and(postLikes.status.eq(status))
                    ).fetchOne();
        }

        return result != null;
    }

    @Override
    public PostLikes findByAccountAndPostAndStatus(Account user, Post post, LikesType likesType) {
        if (user == null) {
            return query.selectFrom(postLikes)
                    .where(
                            postLikes.post.eq(post)
                                .and(postLikes.status.eq(likesType))
                    ).fetchOne();
        } else {
            return query.selectFrom(postLikes)
                    .where(
                            postLikes.account.eq(user)
                                .and(postLikes.post.eq(post))
                                .and(postLikes.status.eq(likesType))
                    ).fetchOne();
        }

    }
}
