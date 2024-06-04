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
    public void deleteMember(String email) {
        memberRepository.deleteById(email);
    }
}