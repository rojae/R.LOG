package kr.or.rlog.report;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static kr.or.rlog.report.QReport.report;

public class ReportRepositorySupportImpl implements ReportRepositorySupport {

    @Autowired
    private JPAQueryFactory query;

    @Override
    public Page<ReportListDto> findReportList(Pageable pageable) {
        QueryResults<ReportListDto> result = query.from(report)
                .select(Projections.constructor(ReportListDto.class,
                        report.id,
                        report.content,
                        report.checkStatus,
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", report.createdDate, "%m/%d/%Y")
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(report.createdDate.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

}
