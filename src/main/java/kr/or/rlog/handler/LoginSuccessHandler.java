package kr.or.rlog.handler;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.UserAccount;
import kr.or.rlog.common.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        HttpSession httpSession = httpServletRequest.getSession();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        httpSession.setAttribute("message", "반갑습니다! " + user.getUsername() + "님");
        httpSession.setAttribute("email", user.getAccount().getEmail());
        httpSession.setAttribute("role", user.getAccount().getRole());

        if(user.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ADMIN.getRoleType())))
            httpServletResponse.sendRedirect("/admin");
        else if(user.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.USER.getRoleType())))
            httpServletResponse.sendRedirect("/main");

    }
}
