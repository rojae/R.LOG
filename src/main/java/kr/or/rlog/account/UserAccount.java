package kr.or.rlog.account;

import kr.or.rlog.account.platform.KakaoUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.UUID;

public class UserAccount extends User {
    private Account account;

    public UserAccount(Account account) {
        super(account.getUserName(), account.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
        this.account = account;
    }

    public UserAccount(KakaoUser kakaoUser){
        super(kakaoUser.getUserName(), String.valueOf(UUID.randomUUID()), Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + kakaoUser.getRole())));
        Account newUser = new Account();
        newUser.setUserName(kakaoUser.getUserName());
        if(StringUtils.isEmpty(kakaoUser.getProfileImage()))
            newUser.setProfileImage("https://blog.rojae.kr/assets/img/illustrations/profiles/profile-2.png");
        else
            newUser.setProfileImage(kakaoUser.getProfileImage());
        newUser.setRole(kakaoUser.getRole());
        newUser.setAccessToken(kakaoUser.getAccessToken().getTokenValue());
        newUser.setAuth(true);
        newUser.setPlatformType(kakaoUser.getPlatformType());
        newUser.setEmail(kakaoUser.getEmail());
        newUser.setPassword(super.getPassword());
        this.account = newUser;
    }

    public Account getAccount() {
        return account;
    }


}