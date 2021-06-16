package kr.or.rlog.post;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.category.Category;
import kr.or.rlog.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

}
