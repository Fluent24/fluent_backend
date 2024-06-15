package com.fluent.controller;

import com.fluent.dto.FavoriteDTO;
import com.fluent.dto.MemberDTO;
import com.fluent.security.JwtUtil;
import com.fluent.service.favorite.FavoriteService;
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
    private final FavoriteService favoriteService;
    private final JwtUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, FavoriteService favoriteService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.favoriteService = favoriteService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<MemberDTO> getMemberByEmail(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7)); // "Bearer " 제거
        MemberDTO memberDTO = memberService.findMemberByEmail(email).orElse(null);
        if (memberDTO != null) {
            List<FavoriteDTO> favoriteDTOS = favoriteService.findFavoritesByEmail(email);
            List<String> favorites = favoriteDTOS.stream()
                                                 .map(FavoriteDTO::getFavorite)
                                                 .collect(Collectors.toList());
            memberDTO.setFavorites(favorites);
            return ResponseEntity.ok(memberDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<MemberDTO> addMember(@RequestHeader("Authorization") String token,
                                               @RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestParam("nickName") String nickName,
                                               @RequestParam("favorites") List<String> favorites) {
        String email = jwtUtil.extractEmail(token.substring(7)); // "Bearer " 제거
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setTier(1L);
        memberDTO.setExp(0L);
        memberDTO.setEmail(email);
        memberDTO.setNickName(nickName);

        MemberDTO savedMember = memberService.addMember(memberDTO, file);
        for (String favorite : favorites) {
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTO.setMemberId(email);
            favoriteDTO.setFavorite(favorite);
            favoriteService.saveFavorite(favoriteDTO);
        }
        return ResponseEntity.ok(savedMember);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberDTO>> getMembersByTier() {
        List<MemberDTO> members = memberService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7)); // "Bearer " 제거
        memberService.deleteMember(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateTier")
    public ResponseEntity<MemberDTO> updateMemberTier(@RequestHeader("Authorization") String token,
                                                 @RequestParam("averageScore") double averageScore) {
        String email = jwtUtil.extractEmail(token.substring(7)); // "Bearer " 제거
        MemberDTO memberDTO = new MemberDTO();
        if (averageScore >= 6) {
            memberDTO = memberService.increaseMemberTier(email);
        } else {
            memberDTO = memberService.decreaseMemberExp(email);
        }
        return ResponseEntity.ok(memberDTO);
    }
}
