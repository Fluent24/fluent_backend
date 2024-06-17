package com.fluent;

import com.fluent.dto.HistoryDTO;
import com.fluent.entity.Member;
import com.fluent.repository.MemberRepository;
import com.fluent.service.history.HistoryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class HistoryServiceTest {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MemberRepository memberRepository;

    //@Test
//    public void testFindHistoryByEmail() {
//        // 'member1@example.com' 이메일을 사용하여 히스토리 검색
//        List<HistoryDTO> histories = historyService.findHistoryByEmail("member1@example.com");
//        assertThat(histories).isNotEmpty();
//        assertThat(histories).hasSize(2); // member1@example.com은 두 개의 히스토리를 가지고 있어야 합니다.
//        assertThat(histories.get(0).getScore()).isIn(5L, 7L); // 두 개의 점수 중 하나가 매치되어야 함
//        assertThat(histories.get(1).getScore()).isIn(5L, 7L);
//    }
//
//    //@Test
//    public void testSaveAndDeleteHistory() {
//        // 새 히스토리 저장 테스트
//        HistoryDTO newHistory = new HistoryDTO(null, 20D, new java.util.Date(), "member3@example.com", 3L);
//        HistoryDTO savedHistory = historyService.saveHistory(newHistory);
//        assertThat(savedHistory).isNotNull();
//        assertThat(savedHistory.getScore()).isEqualTo(20L);
//
//        // 히스토리 삭제 테스트
//        historyService.deleteHistory(savedHistory.getHistoryId());
//        List
//                <HistoryDTO> historiesAfterDeletion = historyService.findHistoryByEmail("member3@example.com");
//        assertThat(historiesAfterDeletion).doesNotContain(savedHistory);
//    }
//
//    @Test
//    @Transactional // 트랜잭션 경계를 설정하여 서비스 메서드 테스트
//    void testSaveHistory() {
//        HistoryDTO historyDTO = new HistoryDTO();
//        historyDTO.setMemberId("member2@example.com");
//        // 기타 필드 설정...
//
//        HistoryDTO savedHistory = historyService.saveHistory(historyDTO);
//
//        // 결과 검증
//        Optional<Member> memberOptional = memberRepository.findByEmail("member2@example.com");
//        assertTrue(memberOptional.isPresent());
//        Member member = memberOptional.get();
//        assertNotNull(member.getExp());
//        assertNotNull(member.getTier());
//    }
}