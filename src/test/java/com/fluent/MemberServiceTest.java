package com.fluent.service.member;

import com.fluent.dto.MemberDTO;
import com.fluent.entity.Member;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.MemberRepository;
import com.fluent.service.FileStorageService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional  // 데이터베이스 트랜잭션 관리 및 테스트 후 롤백을 위해 사용
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    //@Test
    void testFindMemberByEmail() {
        // 데이터베이스에 초기 데이터 입력
        Member member = memberRepository.save(new Member("member1@example.com", "JohnDoe", "https://example.com/path/to/profile1.jpg"));

        // 테스트 실행
        MemberDTO foundMember = memberService.findMemberByEmail("member1@example.com").orElse(null);

        // 검증
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
    }
}