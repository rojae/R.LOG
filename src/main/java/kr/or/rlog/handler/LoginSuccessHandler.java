package kr.or.rlog.handler;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.UserAccount;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        HttpSession httpSession = httpServletRequest.getSession();
        Account account = ((UserAccount) authentication.getPrincipal()).getAccount();

        httpSession.setAttribute("message", "반갑습니다! " + account.getUserName() + "님");
        httpSession.setAttribute("email", account.getEmail());
        httpSession.setAttribute("role", account.getRole());

        httpServletResponse.sendRedirect("/main");
    }
}
