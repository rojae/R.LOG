package kr.or.rlog.post;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.common.Status;
import kr.or.rlog.query.QueryUtils;
import kr.or.rlog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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

    public BooleanExpression eqKeyword(String keyword) {
        return (!StringUtils.hasText(keyword)) ? null : post.title.containsIgnoreCase(keyword);
    }

    public BooleanExpression appendAuthority(Account user){
        if(user == null){
            return post.status.eq(Status.ENABLE);
        }
        else if(SecurityUtils.isAdmin()){
            return post.status.ne(Status.UNABLE);
        }
        // 현재 사용하지 않지만
        // 커뮤니티 게시판처럼, 사용자가 자신의 글 조회 가능.
        else{
            return post.status.eq(Status.ENABLE).or(post.writer.eq(user));
        }
    }

}
