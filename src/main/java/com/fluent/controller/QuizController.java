package com.fluent.controller;

import com.fluent.dto.QuizDTO;
import com.fluent.security.JwtUtil;
import com.fluent.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final JwtUtil jwtUtil;

    @Autowired
    public QuizController(QuizService quizService, JwtUtil jwtUtil) {
        this.quizService = quizService;
        this.jwtUtil = jwtUtil;
    }

    // 이메일을 기반으로 무작위 퀴즈 검색
    @GetMapping
    public ResponseEntity<QuizDTO> getRandomQuiz(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7)); // "Bearer " 제거
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