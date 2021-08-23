package kr.or.rlog.post;

import com.google.common.collect.Sets;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.common.Status;
import kr.or.rlog.query.QueryUtils;
import kr.or.rlog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import static kr.or.rlog.account.QAccount.account;
import static kr.or.rlog.category.QCategory.category;
import static kr.or.rlog.post.QPost.post;

public class PostRepositorySupportImpl implements PostRepositorySupport {

    @Autowired
    JPAQueryFactory query;


    @Override
    public Page<Post> findAllByStatusNotAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category) {
        QueryResults<Post> result = query.selectFrom(post)
                .where(
                        post.status.notIn(status)
                                .and(post.category.eq(category))
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }


    @Override
    public Page<Post> findAllByStatusAndCategoryOrderByCreatedDateDesc(Pageable pageable, Status status, Category category) {
        QueryResults<Post> result = query.selectFrom(post)
                .where(
                        post.status.eq(status)
                                .and(post.category.eq(category))
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    /** ==================================================================
     * @methodName : findPostPage
     * @description : 게시글 페이징 목록 조회
     * @func1 : 게시글의 정보를 가져옴. (페이징)
     * @func2 : 날짜는 YYYYMMDD 문자 형식으로 변경.
     * @author: rojae
     * @date : 2021-08-21
    ==================================================================**/
    @Override
    public Page<PostDto> findPostPage(Pageable pageable, String keyword, Account user) {
        QueryResults<PostDto> result = query.select(
                Projections.constructor(PostDto.class,
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
                        eqKeyword(keyword),
                        appendAuthority(user)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    /** ==================================================================
     * @methodName : getPost
     * @description : 단일 페이지 정보 가져오는 쿼리
     * @func1 : 게시글의 정보를 가져옴.
     * @func2 : 날짜는 YYYYMMDD 문자 형식으로 변경.
     * @func3 : 좋아요 관련된, 컬럼은 이후 셋팅.
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/
    @Override
    public PostDetailDto getPostDetail(Long pId) {
        return query.select(
                Projections.constructor(PostDetailDto.class,
                        post.id,
                        Projections.constructor(CategoryDto.class, category.id, category.categoryName, category.parentId),
                        post.thumbNail,
                        post.header,
                        post.title,
                        post.content,
                        Projections.constructor(AccountDto.class, account.id, account.email, account.userName, account.profileImage),
                        //Expressions.nullExpression(Set.class),          // 댓글 우선 null
                        Expressions.asNumber(0L),                // 글의 좋아요 갯수
                        Expressions.asBoolean(false),           // 사용자의 좋아요 상태
                        post.status,
                        QueryUtils.dateTimeToYYYYMMDD(post.createdDate),
                        QueryUtils.dateTimeToYYYYMMDD(post.modifiedDate)
                ))
                .from(post)
                .leftJoin(category).on(category.id.eq(post.category.id))
                .leftJoin(account).on(account.id.eq(post.writer.id))
                .where(
                        post.id.eq(pId),
                        appendAuthority(SecurityUtils.getAccount())
                )
                .fetchFirst();
    }


    public BooleanExpression eqKeyword(String keyword) {
        return (!StringUtils.hasText(keyword)) ? null : post.title.containsIgnoreCase(keyword);
    }

    public BooleanExpression appendAuthority(Account user) {
        if (user == null) {
            return post.status.eq(Status.ENABLE);
        } else if (SecurityUtils.isAdmin()) {
            return post.status.ne(Status.UNABLE);
        }
        // 현재 사용하지 않지만
        // 커뮤니티 게시판처럼, 사용자가 자신의 글 조회 가능.
        else {
            return post.status.eq(Status.ENABLE).or(post.writer.eq(user));
        }
    }

}
