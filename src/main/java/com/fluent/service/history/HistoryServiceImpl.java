package com.fluent.service.history;

import com.fluent.dto.HistoryDTO;
import com.fluent.entity.History;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
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
        History savedHistory = historyRepository.save(history);
        return EntityMapper.INSTANCE.historyToHistoryDTO(savedHistory);
    }

    @Override
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}