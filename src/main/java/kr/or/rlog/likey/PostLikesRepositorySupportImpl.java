package kr.or.rlog.likey;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.account.QAccount;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.category.QCategory;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import kr.or.rlog.post.PostDto;
import kr.or.rlog.query.QueryUtils;
import kr.or.rlog.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static kr.or.rlog.account.QAccount.account;
import static kr.or.rlog.category.QCategory.category;
import static kr.or.rlog.likey.QPostLikes.postLikes;
import static kr.or.rlog.post.QPost.post;

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

    @Override
    public List<PostDto> findByPostIds(int popularSize) {
        return query.select(Projections.constructor(PostDto.class,
                Projections.constructor(CategoryDto.class, post.category.id, post.category.categoryName, post.category.parentId),
                post.header,
                post.thumbNail,
                post.title,
                Projections.constructor(AccountDto.class, post.writer.email, post.writer.userName, post.writer.profileImage),
                QueryUtils.dateTimeToYYYYMMDD(post.createdDate),
                QueryUtils.dateTimeToYYYYMMDD(post.modifiedDate),
                QueryUtils.postUrl(post.id),
                post.status
        ))
                .from(post)
                .where(
                        post.id.in(JPAExpressions.
                                select(postLikes.post.id)
                                .from(postLikes)
                                .groupBy(postLikes.post)
                                .limit(popularSize)
                                .orderBy(postLikes.count().desc())
                        ),
                        post.status.eq(Status.ENABLE)
                )
                .fetch();
    }


}
