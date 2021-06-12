package kr.or.rlog.comment;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static kr.or.rlog.comment.QComment.comment;

import java.util.List;

public class CommentRepositorySupportImpl implements CommentRepositorySupport{

    @Autowired
    private JPAQueryFactory query;

    @Override
    public List<Comment> findAllByWriterOrStatusOrderByCreatedDateDesc(Account user, Status enable) {
        return query.selectFrom(comment)
                .where(
                        comment.writer.eq(user)
                        .or(comment.status.eq(enable))
                )
                .orderBy(comment.createdDate.desc())
                .fetch();
    }

    @Override
    public List<Comment> findAllByPostAndStatusNotOrderByCreatedDateDesc(Post postId, Status status) {
        return query.selectFrom(comment)
                .where(
                        comment.post.eq(postId)
                        .and(comment.status.eq(status))
                ).orderBy(comment.createdDate.desc())
                .fetch();
    }

    @Override
    public Page<Comment> findAllByWriterAndStatusNot(Pageable pageable, Account writer, Status status) {
        QueryResults<Comment> result = query.selectFrom(comment)
                .where(
                        comment.writer.eq(writer)
                        .and(comment.status.notIn(status))
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
