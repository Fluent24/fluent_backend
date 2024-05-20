package com.fluent.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        // OAuth2 로그인 성공 정보 로그 출력
        System.out.println("Authentication Success. Details: ");
        System.out.println("Authorized Client Registration Id: " + authToken.getAuthorizedClientRegistrationId());
        System.out.println("Principal Name: " + authToken.getName());
        if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser user = (OidcUser) authentication.getPrincipal();
            System.out.println("Email: " + user.getEmail());
            System.out.println("User Attributes: " + user.getAttributes());
        }
        // 리디렉션 또는 추가 처리
        response.sendRedirect("/home");
    }
}