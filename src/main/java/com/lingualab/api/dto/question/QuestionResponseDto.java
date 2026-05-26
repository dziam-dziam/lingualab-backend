package com.lingualab.api.dto.question;

import com.lingualab.api.entity.QuestionType;
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
public class QuestionResponseDto {
    private UUID id;
    private QuestionType type;
    private String title;
    private String description;
    private String placeholder;
    private String imageUrl;
    private String imageKey;
    private String stimulus;
    private String allowedKeys;
    private Integer delayMs;
    private Integer displayOrder;
    private List<QuestionOptionDto> options;
}
