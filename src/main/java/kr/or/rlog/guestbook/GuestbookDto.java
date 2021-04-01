package kr.or.rlog.guestbook;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookDto {
    private Long id;
    private String content;
    private AccountDto writer;
    private LocalDateTime modifiedDate;
    private boolean mine;
    private Status status;

    public GuestbookDto(){}

    public GuestbookDto(Long id, String content, AccountDto writer, LocalDateTime modifiedDate, boolean mine){
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = modifiedDate;
        this.mine = mine;
    }

}
