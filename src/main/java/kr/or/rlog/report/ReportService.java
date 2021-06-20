package kr.or.rlog.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;


    public Page<ReportListDto> getReports(Pageable pageable){
        return reportRepository.findReportList(pageable);
    }

}
