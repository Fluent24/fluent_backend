package com.fluent;

import com.fluent.dto.MemberDTO;
import com.fluent.entity.Member;
import com.fluent.repository.MemberRepository;
import com.fluent.service.member.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional  // 데이터베이스 트랜잭션 관리 및 테스트 후 롤백을 위해 사용
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    //@Test
//    void testFindMemberByEmail() {
//        // 데이터베이스에 초기 데이터 입력
//        Member member = memberRepository.save(new Member("member1@example.com", "JohnDoe", "https://example.com/path/to/profile1.jpg"));
//
//        // 테스트 실행
//        MemberDTO foundMember = memberService.findMemberByEmail("member1@example.com").orElse(null);
//
//        // 검증
//        assertThat(foundMember).isNotNull();
//        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
//    }
//    @Test
//    void  testMemberRepository(){
//        Optional<Member> memberOptional = memberRepository.findByEmail("member2@example.com");
//        if (memberOptional.isPresent()) {
//            Member member = memberOptional.get();
//            System.out.println("Member details:");
//            System.out.println("Email: " + member.getEmail());
//            System.out.println("Exp: " + member.getExp());
//            System.out.println("Tier: " + member.getTier());
//        }
//        return;
//    }

}