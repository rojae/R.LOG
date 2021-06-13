package kr.or.rlog.guestbook;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static kr.or.rlog.guestbook.QGuestbook.guestbook;

public class GuestbookRepositorySupportImpl implements GuestbookRepositorySupport {

    @Autowired
    JPAQueryFactory query;

    @Override
    public Page<Guestbook> findAllByStatusNotOrderByCreatedDateDesc(Pageable pageable, Status unable) {
        QueryResults<Guestbook> result = query.selectFrom(guestbook)
                .where(
                        guestbook.status.notIn(unable)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(guestbook.createdDate.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<Guestbook> findAllByWriterAndStatusNotOrStatusOrderByCreatedDateDesc(Pageable pageable, Account writer, Status unable, Status enable) {
        QueryResults<Guestbook> result;

        if(writer == null) {
            result = query.selectFrom(guestbook)
                    .where(
                            (guestbook.status.notIn(unable).and(guestbook.status.in(enable)))
                    )
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(guestbook.createdDate.desc())
                    .fetchResults();
        }else{
            result = query.selectFrom(guestbook)
                    .where(
                            guestbook.writer.eq(writer)
                                    .or(guestbook.status.in(enable))
                    )
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(guestbook.createdDate.desc())
                    .fetchResults();
        }
        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
