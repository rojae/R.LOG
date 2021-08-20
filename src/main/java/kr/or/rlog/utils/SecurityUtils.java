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

    public static Account getAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount account = (UserAccount) authentication.getPrincipal();
        return account.getAccount();
    }

    public static boolean isAdmin(){
        Collection<? extends GrantedAuthority> role = getRole();
        return role.stream().anyMatch(r -> {
            return r.getAuthority().equals("ROLE_ADMIN");
        });
    }

}
