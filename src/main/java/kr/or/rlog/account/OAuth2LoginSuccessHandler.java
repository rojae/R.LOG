package kr.or.rlog.account;

import kr.or.rlog.account.platform.KakaoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Autowired
    AccountService accountService;

    /*
        카카오톡 SNS를 사용하여 로그인을 하였지만
        세션은 일반 로그인 사용자처럼 변환하여 처리해준다.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        KakaoUser kakaoUser = new KakaoUser(((DefaultOAuth2User) authentication.getPrincipal()).getAttributes(), ((DefaultOAuth2User) authentication.getPrincipal()).getAttribute("accessToken"), "ROLE");
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                this.getUserAccount(kakaoUser.getEmail()),
                String.valueOf(UUID.randomUUID()),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + kakaoUser.getRole()))
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        response.sendRedirect("/index");
    }

    public UserDetails getUserAccount(String email){
        return accountService.loadUserByUsername(email);
    }

}


