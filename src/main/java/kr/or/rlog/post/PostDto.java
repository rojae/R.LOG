package kr.or.rlog.post;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.CategoryDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private CategoryDto category;
    private String header;
    private String title;
    private AccountDto writer;
    private String createdDate;
    private String modifiedDate;
    private String url;

    public PostDto(){

    }

}
