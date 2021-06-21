package kr.or.rlog.report;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.CheckStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private Long id;
    private String content;
    private AccountDto writer;
    private CheckStatus checkStatus;
    private String createdDate;

    public ReportDto(Long id, String content, AccountDto writer, CheckStatus checkStatus, String createdDate){
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.checkStatus = checkStatus;
        this.createdDate = createdDate;
    }

}
