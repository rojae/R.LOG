package kr.or.rlog.report;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.CheckStatus;
import kr.or.rlog.common.ReadStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportListDto {
    private Long id;
    private String content;
    private ReadStatus readStatus;
    private CheckStatus checkStatus;
    private String createdDate;

    public ReportListDto(Long id, String content, ReadStatus readStatus, CheckStatus checkStatus, String createdDate){
        this.id = id;
        this.content = content;
        this.readStatus = readStatus;
        this.checkStatus = checkStatus;
        this.createdDate = createdDate;
    }

}
