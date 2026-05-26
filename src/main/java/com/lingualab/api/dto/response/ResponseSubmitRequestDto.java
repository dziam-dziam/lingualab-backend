package com.lingualab.api.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSubmitRequestDto {
    private UUID sessionId;
    private String userAgent;

    @Valid
    @NotEmpty
    private List<AnswerRequestDto> answers;
}
