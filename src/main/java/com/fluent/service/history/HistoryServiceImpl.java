package com.fluent.service.history;

import com.fluent.dto.HistoryDTO;
import com.fluent.entity.History;
import com.fluent.entity.Member;
import com.fluent.entity.Quiz;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.HistoryRepository;
import com.fluent.repository.MemberRepository;
import com.fluent.repository.QuizRepository;
import com.fluent.service.member.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    private final QuizRepository quizRepository;

    private final MemberRepository memberRepository;


    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, QuizRepository quizRepository, MemberRepository memberRepository) {
        this.historyRepository = historyRepository;
        this.quizRepository = quizRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<HistoryDTO> findHistoryByEmail(String email) {
        return historyRepository.findByMember_Email(email).stream()
                                .map(EntityMapper.INSTANCE::historyToHistoryDTO)
                                .collect(Collectors.toList());
    }

    @Override
    public HistoryDTO saveHistory(HistoryDTO historyDTO) {
        History history = EntityMapper.INSTANCE.historyDTOToHistory(historyDTO);

        // Member 처리
        Optional<Member> memberOptional = memberRepository.findByEmail(historyDTO.getMemberId());
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            history.setMember(member);

            System.out.println("Member details before update:");
            System.out.println("Email: " + member.getEmail());
            System.out.println("Exp: " + member.getExp());
            System.out.println("Tier: " + member.getTier());

            if (member.getExp() == null) {
                member.setExp(0L);
            }

            if (member.getExp() <= 90) {
                member.setExp(member.getExp() + 10);
            } else {
                member.setExp(100L);
            }
            memberRepository.save(member);

            System.out.println("Member details after update:");
            System.out.println("Exp: " + member.getExp());
        }

        // Quiz 처리
        Optional<Quiz> quizOptional = quizRepository.findById(historyDTO.getQuizId());
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            history.setQuiz(quiz);

            System.out.println("Quiz details before update:");
            System.out.println("Quiz ID: " + quiz.getQuizId());
            System.out.println("Question: " + quiz.getQuestion());
            System.out.println("Answer Vec: " + quiz.getAnswerVec());
        }

        // 병합된 엔티티를 저장
        History savedHistory = historyRepository.save(history);
        System.out.println("Saved History ID: " + savedHistory.getHistoryId());
        System.out.println("History Member ID: " + historyDTO.getMemberId());

        return EntityMapper.INSTANCE.historyToHistoryDTO(savedHistory);
    }


    @Override
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}