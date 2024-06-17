package com.fluent.controller;

import com.fluent.dto.HistoryDTO;
import com.fluent.security.JwtUtil;
import com.fluent.service.history.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {
    private final HistoryService historyService;
    private final JwtUtil jwtUtil;

    @Autowired
    public HistoryController(HistoryService historyService, JwtUtil jwtUtil) {
        this.historyService = historyService;
        this.jwtUtil = jwtUtil;
    }

    // 이메일을 기반으로 히스토리 검색
    @GetMapping
    public ResponseEntity<List<HistoryDTO>> getHistoriesByEmail(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7)); // "Bearer " 제거
        List<HistoryDTO> histories = historyService.findHistoryByEmail(email);
        if (histories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(histories);
    }

    // 히스토리 생성
    @PostMapping
    public ResponseEntity<HistoryDTO> createHistory(@RequestHeader("Authorization") String token, @RequestBody HistoryDTO historyDTO) {
        String email = jwtUtil.extractEmail(token.substring(7));
        historyDTO.setMemberId(email);
        System.out.println(historyDTO.getQuizId());
        System.out.println(historyDTO);
        HistoryDTO savedHistory = historyService.saveHistory(historyDTO);
        return ResponseEntity.ok(savedHistory);
    }

    // 히스토리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return ResponseEntity.ok().build();
    }
}