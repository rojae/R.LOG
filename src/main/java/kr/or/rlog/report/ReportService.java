package kr.or.rlog.report;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;


    public Page<ReportListDto> getReports(Pageable pageable) {
        return reportRepository.findReportList(pageable);
    }

    public ReportDto findOne(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent() && report.get().getWriter() != null) {
            return new ReportDto(report.get().getId(), report.get().getContent(),
                    new AccountDto(report.get().getWriter().getEmail(), report.get().getWriter().getUserName(), report.get().getWriter().getProfileImage()),
                    report.get().getCheckStatus(), TimeUtils.dateTimeToYYYYMMDD(report.get().getCreatedDate()));
        } else return report.map(value -> new ReportDto(value.getId(), value.getContent(),
                null, value.getCheckStatus(), TimeUtils.dateTimeToYYYYMMDD(value.getCreatedDate()))).orElse(null);
    }
}