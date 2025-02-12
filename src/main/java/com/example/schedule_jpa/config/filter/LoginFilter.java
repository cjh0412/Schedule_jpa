package com.example.schedule_jpa.config.filter;

import com.example.schedule_jpa.exception.MemberException;
import com.example.schedule_jpa.exception.errorcode.MemberErrorCode;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PatternMatchUtils;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static String[] WHITE_LIST = {"/", "/members/signUp", "/members/login"};
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        log.info("로그인 필터 로직 실행");

        if(!isWhiteList(requestURI)){
            HttpSession session = request.getSession(false);

            if(session == null || session.getAttribute("token") == null){
                throw new MemberException(MemberErrorCode.LOGIN_REQUIRED);
            }
        }

        filterChain.doFilter(request, response);
    }


    private boolean isWhiteList(String requestURI){
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
