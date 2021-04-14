package kr.or.rlog.common;


import kr.or.rlog.utils.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggerFilter extends GenericFilterBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(((HttpServletRequest)request).getRequestURI());
        chain.doFilter(request, response);       // next filter
        stopWatch.stop();

        logger.info("--- IP : " + ClientUtils.getClientIP((HttpServletRequest) request));
        logger.info("--- Agent : " + ((HttpServletRequest) request).getHeader("User-Agent"));
        logger.info("--- StopWatch : " + stopWatch.toString());
        logger.info("--- Refer : " + ((HttpServletRequest) request).getHeader("referer"));
    }
}