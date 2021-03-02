package kr.or.rlog.handler;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.UserAccount;
import kr.or.rlog.common.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class LoginSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        HttpSession httpSession = httpServletRequest.getSession();
        UserAccount user = (UserAccount) authentication.getPrincipal();

        // Security가 요청을 가로챈 경우 사용자가 원래 요청했던 URI 정보를 저장한 객체
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(httpServletRequest, httpServletResponse);
        String uri = "/index";

        // 있을 경우 URI 등 정보를 가져와서 사용
        if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();

            // 세션에 저장된 객체를 다 사용한 뒤에는 지워줘서 메모리 누수 방지
            requestCache.removeRequest(httpServletRequest, httpServletResponse);

            System.out.println(uri);
        }

        // 세션 Attribute 확인
        Enumeration<String> list = httpServletRequest.getSession().getAttributeNames();
        while (list.hasMoreElements()) {
            System.out.println(list.nextElement());
        }

        httpServletResponse.sendRedirect(uri);


/*
        httpSession.setAttribute("message", "반갑습니다! " + user.getUsername() + "님");
        httpSession.setAttribute("email", user.getAccount().getEmail());
        httpSession.setAttribute("role", user.getAccount().getRole());


        if(user.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.ADMIN.getRoleType())))
            httpServletResponse.sendRedirect("/admin");
        else if(user.getAuthorities().contains(new SimpleGrantedAuthority(RoleType.USER.getRoleType())))
            httpServletResponse.sendRedirect("/main");
*/

    }
}
