package com.fluent.controller;

import com.fluent.security.CustomOAuth2Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    // 사용자 프로필 정보 조회
    @GetMapping("/profile")
    public Map<String, Object> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            CustomOAuth2Member customMember = new CustomOAuth2Member(oauth2User);
            Map<String, Object> profile = customMember.getAttributes();
            // 이메일 정보를 추가합니다.
            profile.put("email", customMember.getEmail());
            return profile;
        }
        return Map.of("error", "No user authenticated");
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.clearContext();  // Spring Security의 Context를 클리어하여 로그아웃 처리
            request.getSession().invalidate();    // 세션 무효화
        }
        return "You have been logged out successfully";  // 로그아웃 성공 메시지
    }
}