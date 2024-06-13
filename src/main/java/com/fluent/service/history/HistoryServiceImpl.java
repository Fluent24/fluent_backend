package com.fluent.service.history;

import com.fluent.dto.HistoryDTO;
import com.fluent.entity.History;
import com.fluent.entity.Member;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.HistoryRepository;
import com.fluent.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository, MemberRepository memberRepository) {
        this.historyRepository = historyRepository;
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
        History savedHistory = historyRepository.save(history);

        // 멤버의 exp 업데이트
        Optional<Member> memberOptional = memberRepository.findByEmail(historyDTO.getMemberId());
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if(member.getExp() <= 90) {
                member.setExp(member.getExp() + 10);
            }
            memberRepository.save(member);
        }

        return EntityMapper.INSTANCE.historyToHistoryDTO(savedHistory);
    }

    @Override
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}