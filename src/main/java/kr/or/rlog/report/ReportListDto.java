package kr.or.rlog.report;

import kr.or.rlog.common.CheckStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportListDto {
    private Long id;
    private String content;
    private CheckStatus checkStatus;
    private String createdDate;

    public ReportListDto(Long id, String content, CheckStatus checkStatus, String createdDate){
        this.id = id;
        this.content = content;
        this.checkStatus = checkStatus;
        this.createdDate = createdDate;
    }

}
