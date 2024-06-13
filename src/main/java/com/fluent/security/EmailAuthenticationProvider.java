package com.fluent.security;

import com.fluent.dto.MemberDTO;
import com.fluent.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public EmailAuthenticationProvider(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        Optional<MemberDTO> memberOptional = memberService.findMemberByEmail(email);
        if (memberOptional.isPresent()) {
            Map<String, Object> tokens = jwtUtil.generateTokens(email);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, "", List.of());
            return new UsernamePasswordAuthenticationToken(userDetails, tokens.get("accessToken"), userDetails.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Member not found with email: " + email);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}