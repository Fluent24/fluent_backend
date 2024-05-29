package com.fluent.controller;

import com.fluent.dto.HistoryDTO;
import com.fluent.entity.History;
import com.fluent.service.history.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {
    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    // 이메일을 기반으로 히스토리 검색
    @GetMapping
    public ResponseEntity<List<HistoryDTO>> getHistoriesByEmail(@RequestParam String email) {
        List<HistoryDTO> histories = historyService.findHistoryByEmail(email);
        if (histories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(histories);
    }

    // 히스토리 생성
    @PostMapping
    public ResponseEntity<HistoryDTO> createHistory(@RequestBody HistoryDTO historyDTO) {
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