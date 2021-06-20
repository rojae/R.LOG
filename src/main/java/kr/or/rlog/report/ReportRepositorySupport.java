package kr.or.rlog.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositorySupport {
    public Page<ReportListDto> findReportList(Pageable pageable);
}
