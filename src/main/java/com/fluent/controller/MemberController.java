package com.fluent.controller;

import com.fluent.dto.FavoriteDTO;
import com.fluent.dto.MemberDTO;
import com.fluent.service.favorite.FavoriteService;
import com.fluent.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final FavoriteService favoriteService;

    @Autowired
    public MemberController(MemberService memberService, FavoriteService favoriteService) {
        this.memberService = memberService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberDTO> getMemberByEmail(@PathVariable String email) {
        MemberDTO memberDTO = memberService.findMemberByEmail(email).orElse(null);
        return memberDTO != null ? ResponseEntity.ok(memberDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<MemberDTO> addMember(@RequestParam("file") MultipartFile file,
                                               @RequestParam("email") String email,
                                               @RequestParam("nickName") String nickName,
                                               @RequestParam("favorites") List<String> favorites) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail(email);
        memberDTO.setNickName(nickName);
        System.out.println(favorites);
        MemberDTO savedMember = memberService.addMember(memberDTO, file);
        for (String favorite : favorites){
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTO.setMemberId(email);
            favoriteDTO.setFavorite(favorite);
            favoriteService.saveFavorite(favoriteDTO);
        }
        return ResponseEntity.ok(savedMember);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);
        return ResponseEntity.ok().build();
    }
}
