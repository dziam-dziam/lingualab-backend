package com.lingualab.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponseDto {
    private UUID id;
    private UUID questionId;
    private String questionTitle;
    private String answerText;
    private String selectedOption;
    private Long reactionTimeMs;
    private Instant submittedAt;
}
