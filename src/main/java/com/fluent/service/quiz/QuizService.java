package com.fluent.service.quiz;

import com.fluent.dto.QuizDTO;
import com.fluent.entity.Quiz;

import java.util.List;

public interface QuizService {
    QuizDTO findRandomQuiz(String email);
    QuizDTO saveQuiz(QuizDTO quizDTO);
    void deleteQuiz(Long id);
    QuizDTO findQuizById(Long quizId);
    void requestAndStoreQuizzes(String category);
}