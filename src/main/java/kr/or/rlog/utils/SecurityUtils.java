package kr.or.rlog.utils;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityUtils {

    public static boolean isLogined(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated();
    }

    public static Collection<? extends GrantedAuthority> getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }

    /** ==================================================================
     * @methodName : getAccount()
     * @description : 사용자 계정객체 반환. (익명 사용자 null 반환)
     * @func1 : anonymousUser check
     *  - https://stackoverflow.com/questions/26101738/why-is-the-anonymoususer-authenticated-in-spring-security
     * @author: rojae
     * @date : 2021-08-21
     ==================================================================**/

    public static Account getAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal().equals("anonymousUser"))
            return null;
        UserAccount account = (UserAccount) authentication.getPrincipal();
        return (account.getAccount() == null)? null : account.getAccount();
    }

    public static boolean isAdmin(){
        Collection<? extends GrantedAuthority> role = getRole();
        return role.stream().anyMatch(r -> {
            return r.getAuthority().equals("ROLE_ADMIN");
        });
    }

}
