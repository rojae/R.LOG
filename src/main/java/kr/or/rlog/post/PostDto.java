package kr.or.rlog.post;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.common.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private CategoryDto category;
    private String header;
    private String thumbNail;
    private String title;
    private AccountDto writer;
    private String createdDate;
    private String modifiedDate;
    private String url;
    private Status status;

    public PostDto(){

    }

}
