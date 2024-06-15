package com.fluent.controller;

import com.fluent.dto.MemberDTO;
import com.fluent.entity.Member;
import com.fluent.security.JwtUtil;
import com.fluent.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public AuthenticationController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        Optional<MemberDTO> memberOptional = memberService.findMemberByEmail(email);

        if (memberOptional.isPresent()) {
            Map<String, Object> tokens = jwtUtil.generateTokens(email);
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Member not found");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");

        if (!jwtUtil.isTokenExpired(refreshToken)) {
            String email = jwtUtil.extractEmail(refreshToken);
            Map<String, Object> tokens = jwtUtil.generateTokens(email);
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }
    }
}