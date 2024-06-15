package com.fluent.service.member;

import com.fluent.dto.MemberDTO;
import com.fluent.entity.Member;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.MemberRepository;
import com.fluent.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    //private final S3Service s3Service;

//    @Autowired
//    public MemberServiceImpl(MemberRepository memberRepository, S3Service s3Service) {
//        this.memberRepository = memberRepository;
//        this.s3Service = s3Service;
//    }
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<MemberDTO> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                               .map(EntityMapper.INSTANCE::memberToMemberDTO);
    }


    @Override
    public MemberDTO addMember(MemberDTO memberDTO, MultipartFile file) {
//        if (file != null && !file.isEmpty()) {
//            try {
//                String fileUrl = s3Service.uploadFile(file);
//                memberDTO.setProfilePictureUrl(fileUrl);
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload file to S3", e);
//            }
//        } else {
//            memberDTO.setProfilePictureUrl(null); // 파일이 없을 경우 처리
//        }

        Member member = EntityMapper.INSTANCE.memberDTOToMember(memberDTO);
        Member savedMember = memberRepository.save(member);

        return EntityMapper.INSTANCE.memberToMemberDTO(savedMember);
    }

    @Override
    public List<MemberDTO> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                      .map(EntityMapper.INSTANCE::memberToMemberDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public void deleteMember(String email) {
        memberRepository.deleteById(email);
    }

    @Override
    public MemberDTO increaseMemberTier(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setTier(member.getTier() + 1);
            Member savedMember = memberRepository.save(member);
            return EntityMapper.INSTANCE.memberToMemberDTO(savedMember);
        } else {
            throw new RuntimeException("Member not found with email: " + email);
        }
    }

    @Override
    public MemberDTO decreaseMemberExp(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setExp(75L);  // 예시로 설정된 경험치 값
            Member savedMember = memberRepository.save(member);
            return EntityMapper.INSTANCE.memberToMemberDTO(savedMember);
        } else {
            throw new RuntimeException("Member not found with email: " + email);
        }
    }
}