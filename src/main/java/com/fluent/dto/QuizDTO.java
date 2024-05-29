package com.fluent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {
    private Long quizId;
    private String question;
    private String favorite;
    private String refAudio;
    private Long tier;
    private Long answerVec;
}
