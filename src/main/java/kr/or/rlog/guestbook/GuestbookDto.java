package kr.or.rlog.guestbook;

import kr.or.rlog.account.AccountDto;
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

    public GuestbookDto(){}

    public GuestbookDto(Long id, String content, AccountDto writer, LocalDateTime modifiedDate){
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = modifiedDate;
    }

}
