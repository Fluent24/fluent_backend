package com.fluent.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2Member implements OAuth2User {

    private OAuth2User oauth2User;

    public CustomOAuth2Member(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        Object id = oauth2User.getAttribute("id");
        if (id != null) {
            return String.valueOf(id);  // Long을 String으로 안전하게 변환
        }
        return null; // 또는 적절한 기본값 반환
    }

    public String getEmail() {
        System.out.println((String) oauth2User.getAttributes().get("account_email"));
        return (String) oauth2User.getAttributes().get("account_email");
    }
}