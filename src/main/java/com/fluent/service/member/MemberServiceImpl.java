package com.fluent.service.member;

import com.fluent.dto.MemberDTO;
import com.fluent.entity.Member;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.MemberRepository;
import com.fluent.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, FileStorageService fileStorageService) {
        this.memberRepository = memberRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Optional<MemberDTO> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                               .map(EntityMapper.INSTANCE::memberToMemberDTO);
    }

    @Override
    public MemberDTO addMember(MemberDTO memberDTO, MultipartFile file) {
        String filename = fileStorageService.storeFile(file);
        memberDTO.setProfilePictureUrl(filename);
        Member member = EntityMapper.INSTANCE.memberDTOToMember(memberDTO);
        Member savedMember = memberRepository.save(member);
        return EntityMapper.INSTANCE.memberToMemberDTO(savedMember);
    }

    @Override
    public List<MemberDTO> findMembersByTier(Long tier) {
        List<Member> members = memberRepository.findByTier(tier);
        return members.stream()
                      .map(EntityMapper.INSTANCE::memberToMemberDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public void deleteMember(String email) {
        memberRepository.deleteById(email);
    }

    @Override
    public void increaseMemberTier(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setTier(member.getTier() + 1);
            memberRepository.save(member);
        }
    }

    @Override
    public void decreaseMemberExp(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setExp(75L);
            memberRepository.save(member);
        }
    }
}