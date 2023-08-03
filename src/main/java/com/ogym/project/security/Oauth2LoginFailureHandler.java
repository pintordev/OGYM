package com.ogym.project.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class Oauth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException, ServletException {

        String exceptionMessage;

        if (exception instanceof BadCredentialsException) {
            exceptionMessage = exception.getMessage();
        } else if (exception instanceof InternalAuthenticationServiceException) {
            exceptionMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            exceptionMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            exceptionMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            exceptionMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }

        exceptionMessage = URLEncoder.encode(exceptionMessage, "UTF-8"); // 한글 인코딩 깨진 문제 방지
        setDefaultFailureUrl("/user/login?error=true&exception=" + exceptionMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}
