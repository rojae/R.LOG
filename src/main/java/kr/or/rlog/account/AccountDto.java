package kr.or.rlog.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private String email;
    private String userName;
    private String profileImage;

    public AccountDto(){

    }

    public AccountDto(String email, String userName, String profileImage){
        this.email = email;
        this.userName = userName;
        this.profileImage = profileImage;
    }

}
