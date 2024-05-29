package com.fluent;

import com.fluent.dto.QuizDTO;
import com.fluent.repository.QuizRepository;
import com.fluent.service.favorite.FavoriteService;
import com.fluent.service.quiz.QuizService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuizServiceImplTest {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private FavoriteService favoriteService;

    @Test
    public void testFindRandomQuiz() {
        // 예시 이메일에 대해 무작위 퀴즈 찾기
        String email = "member1@example.com"; // 데이터베이스에 존재하는 이메일이라고 가정
        QuizDTO quizDTO = quizService.findRandomQuiz(email);

        assertNotNull(quizDTO, "QuizDTO should not be null");
        assertTrue(quizDTO.getFavorite().equals("여행") || quizDTO.getFavorite().equals("운동"));
    }

    @Test
    public void testSaveQuiz() {
        QuizDTO newQuizDTO = new QuizDTO();
        newQuizDTO.setQuestion("What is the largest planet?");
        newQuizDTO.setFavorite("여행");
        newQuizDTO.setRefAudio("path/to/audio.mp3");
        newQuizDTO.setTier(2L);
        newQuizDTO.setAnswerVec(123456L);

        QuizDTO savedQuizDTO = quizService.saveQuiz(newQuizDTO);
        assertNotNull(savedQuizDTO);
        assertNotNull(savedQuizDTO.getQuizId());

        // 저장된 퀴즈가 데이터베이스에 존재하는지 확인
        assertTrue(quizRepository.findById(savedQuizDTO.getQuizId()).isPresent());
    }

    @Test
    public void testDeleteQuiz() {
        // 새 퀴즈 저장 후 삭제
        QuizDTO newQuizDTO = new QuizDTO();
        newQuizDTO.setQuestion("What is the smallest planet?");
        newQuizDTO.setFavorite("여행");
        newQuizDTO.setRefAudio("path/to/another_audio.mp3");
        newQuizDTO.setTier(1L);
        newQuizDTO.setAnswerVec(654321L);

        QuizDTO savedQuizDTO = quizService.saveQuiz(newQuizDTO);
        Long savedQuizId = savedQuizDTO.getQuizId();
        assertNotNull(savedQuizId);

        quizService.deleteQuiz(savedQuizId);

        // 삭제 후 퀴즈가 데이터베이스에 존재하지 않는지 확인
        assertFalse(quizRepository.findById(savedQuizId).isPresent());
    }
}