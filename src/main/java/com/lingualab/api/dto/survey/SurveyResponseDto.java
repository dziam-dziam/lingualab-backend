package com.lingualab.api.dto.survey;

import com.lingualab.api.dto.question.QuestionResponseDto;
import com.lingualab.api.entity.SurveyStatus;
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
public class SurveyResponseDto {
    private UUID id;
    private String title;
    private String description;
    private SurveyStatus status;
    private String publicId;
    private String publicUrl;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant publishedAt;
    private List<QuestionResponseDto> questions;
}
