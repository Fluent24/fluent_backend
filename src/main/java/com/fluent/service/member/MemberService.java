package com.fluent.service.member;

import com.fluent.dto.MemberDTO;
import com.fluent.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<MemberDTO> findMemberByEmail(String email);
    MemberDTO addMember(MemberDTO member, MultipartFile file);
    List<MemberDTO> findMembersByTier(Long tier);
    void deleteMember(String email);
    void increaseMemberTier(String email);
    void decreaseMemberExp(String email);
}