package com.fluent.controller;

import com.fluent.dto.QuizDTO;
import com.fluent.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // 이메일을 기반으로 무작위 퀴즈 검색
    @GetMapping
    public ResponseEntity<QuizDTO> getRandomQuiz(@RequestParam String email) {
        QuizDTO quizDTO = quizService.findRandomQuiz(email);
        if (quizDTO != null) {
            return ResponseEntity.ok(quizDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 퀴즈 생성
    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) {
        QuizDTO savedQuiz = quizService.saveQuiz(quizDTO);
        return ResponseEntity.ok(savedQuiz);
    }

    // 퀴즈 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok().build();
    }
}