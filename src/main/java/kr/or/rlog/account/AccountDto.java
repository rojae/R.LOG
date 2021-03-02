package kr.or.rlog.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private String email;
    private String userName;

    public AccountDto(){

    }

    public AccountDto(String email, String userName){
        this.email = email;
        this.userName = userName;
    }

}
