package com.lingualab.api.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestDto {
    @NotNull
    private UUID questionId;

    private String answerText;
    private String selectedOption;
    private String pressedKey;
    private Long reactionTimeMs;
}
