package kr.or.rlog.visit;

import kr.or.rlog.utils.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionConfig implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(SessionConfig.class);

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {

        HttpSession session = arg0.getSession();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        VisitorRepository repository = (VisitorRepository) wac.getBean("visitorRepository");

        // 방문자 수를 알기 위해서 Session Created 시
        // visitor에 접속자 정보를 넣는다.
        logger.info("----------------  SESSION CREATED  -------------------");
        logger.info("--- IP : " + ClientUtils.getClientIP(req));
        logger.info("--- Agent : " + req.getHeader("User-Agent"));
        logger.info("--- Refer : " + req.getHeader("referer"));
        logger.info("-------------------------------------------------------");

        repository.save(new Visitor(ClientUtils.getClientIP(req), req.getHeader("referer"), req.getHeader("User-Agent")));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
    }

}
