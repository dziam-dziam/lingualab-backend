package com.lingualab.api.dto.question;

import com.lingualab.api.entity.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDto {
    @NotNull
    private QuestionType type;

    @NotBlank
    private String title;

    private String description;
    private String placeholder;
    private String imageUrl;
    private String imageKey;
    private String stimulus;
    private String allowedKeys;
    private Integer delayMs;

    @NotNull
    private Integer displayOrder;

    @Valid
    private List<QuestionOptionDto> options;
}
