package kr.or.rlog.guestbook;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.Status;
import kr.or.rlog.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookDto {
    private Long id;
    private String content;
    private AccountDto writer;
    private String modifiedDate;
    private boolean mine;
    private Status status;

    public GuestbookDto(){}

    public GuestbookDto(Long id, String content, AccountDto writer, LocalDateTime modifiedDate, boolean mine){
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = TimeUtils.dateTimeToYYYYMMDD(modifiedDate);
        this.mine = mine;
    }

}
