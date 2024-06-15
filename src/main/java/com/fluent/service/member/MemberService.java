package com.fluent.service.member;

import com.fluent.dto.MemberDTO;
import com.fluent.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<MemberDTO> findMemberByEmail(String email);
    MemberDTO addMember(MemberDTO member, MultipartFile file);
    List<MemberDTO> findAllMembers();
    void deleteMember(String email);
    MemberDTO increaseMemberTier(String email);
    MemberDTO decreaseMemberExp(String email);
}