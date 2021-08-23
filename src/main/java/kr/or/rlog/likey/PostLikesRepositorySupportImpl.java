package kr.or.rlog.likey;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.PostDto;
import kr.or.rlog.query.QueryUtils;
import kr.or.rlog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static kr.or.rlog.likey.QPostLikes.postLikes;
import static kr.or.rlog.post.QPost.post;

public class PostLikesRepositorySupportImpl implements PostLikesRepositorySupport {

    @Autowired
    JPAQueryFactory query;


    /** ==================================================================
     * @methodName : getLikeCount
     * @description : 현재 글의 좋아요 수.
     * @author: rojae
     * @date : 2021-08-21
    ==================================================================**/
    public Long getLikeCount(Long pId){
        return query.select(postLikes.id)
                .from(postLikes)
                .where(
                        postLikes.post.id.eq(pId),
                        eqEnable()
                )
                .fetchCount();
    }

    /** ==================================================================
     * @methodName : isLike
     * @description : 현재 글에, 사용자가 좋아요를 눌렀는지 반환
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/
    @Override
    public boolean isLike(Long pId) {
        return query.selectOne()
                .from(postLikes)
                .where(
                        postLikes.post.id.eq(pId),
                        eqAccount(SecurityUtils.getAccount()),
                        eqEnable()
                ).fetchOne() != null;
    }

    /** ==================================================================
     * @methodName : getPostLikes
     * @description : 현재 글에 해당하는, 사용자의 활성화된 좋아요 객체를 반환.
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/
    @Override
    public PostLikes getPostLikes(Long pId) {
        return query.selectFrom(postLikes)
                .where(
                        postLikes.post.id.eq(pId),
                        eqAccount(SecurityUtils.getAccount()),
                        eqEnable()
                ).fetchOne();
    }

    /** ==================================================================
     * @methodName : findByPostIds
     * @description : 상위 인기글의 정보를 가져오는 쿼리.
     * @func1 : 상위 인기 글 추출.
     * @func2 : 날짜 형식 YYYYMMDD 변환.
     * @func3 : URL 형식 변환.
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/
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

    public BooleanExpression eqAccount(Account account) {
        return (account == null) ? null : postLikes.account.eq(account);
    }

    public BooleanExpression eqEnable(){
        return postLikes.status.eq(LikesType.valueOf(Status.ENABLE.name()));
    }

}
