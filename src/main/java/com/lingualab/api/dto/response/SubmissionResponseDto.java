package com.lingualab.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResponseDto {
    private UUID sessionId;
    private Instant startedAt;
    private Instant completedAt;
    private List<AnswerResponseDto> answers;
}
