package kr.or.rlog.handler;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

        if(e instanceof DisabledException) {
            httpSession.setAttribute("loginResult", "메일 인증이 되지 않은 사용자입니다");
            //errormsg = MessageUtils.getMessage("error.DisabledException");
        } else if(e instanceof AccountExpiredException) {
            httpSession.setAttribute("loginResult", "카카오톡 회원입니다. 카카오톡을 통해서 로그인을 해야합니다.");
            //errormsg = MessageUtils.getMessage("error.DisabledByKakao");
        } else{
            httpSession.setAttribute("loginResult", "존재하지 않는 사용자 혹은 비밀번호입니다");
            //errormsg = MessageUtils.getMessage("error.BadCredentials");
        }

        httpServletResponse.sendRedirect("/login");
    }
}
