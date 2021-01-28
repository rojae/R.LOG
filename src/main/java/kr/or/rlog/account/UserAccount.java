package kr.or.rlog.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collections;

public class UserAccount extends User {
    private Account account;

    public UserAccount(Account account) {
        super(account.getUserName(), account.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "account=" + account +
                '}';
    }
}