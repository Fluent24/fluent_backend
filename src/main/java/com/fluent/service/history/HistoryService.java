package com.fluent.service.history;

import com.fluent.dto.HistoryDTO;
import com.fluent.entity.History;

import java.util.List;

public interface HistoryService {
    List<HistoryDTO> findHistoryByEmail(String email);
    HistoryDTO saveHistory(HistoryDTO historyDTO);
    void deleteHistory(Long id);
}
