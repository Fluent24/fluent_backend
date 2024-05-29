package com.fluent.controller;

import com.fluent.dto.MemberDTO;
import com.fluent.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberDTO> getMemberByEmail(@PathVariable String email) {
        MemberDTO memberDTO = memberService.findMemberByEmail(email).orElse(null);
        return memberDTO != null ? ResponseEntity.ok(memberDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<MemberDTO> addMember(@RequestParam("file") MultipartFile file,
                                               @RequestParam("email") String email,
                                               @RequestParam("nickName") String nickName) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(email);
        memberDTO.setNickName(nickName);
        MemberDTO savedMember = memberService.addMember(memberDTO, file);
        return ResponseEntity.ok(savedMember);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);
        return ResponseEntity.ok().build();
    }
}
