package com.fluent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
    private Long historyId;
    private Double score;
    private Date solverDate;
    private String memberId;
    private Long quizId;
}
