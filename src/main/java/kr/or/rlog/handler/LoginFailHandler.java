package kr.or.rlog.handler;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFailHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException, IOException {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("message", "로그인을 실패하였습니다. 다시 시도해주세요.");

        httpServletResponse.sendRedirect("/login");
    }
}
