package com.lingualab.api.mapper;

import com.lingualab.api.dto.survey.SurveyRequestDto;
import com.lingualab.api.dto.survey.SurveyResponseDto;
import com.lingualab.api.entity.Survey;
import com.lingualab.api.entity.SurveyStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.UUID;

@Component
public class SurveyMapper {
    private final QuestionMapper questionMapper;
    private final String publicFrontendUrl;

    public SurveyMapper(QuestionMapper questionMapper, @Value("${lingualab.public-frontend-url}") String publicFrontendUrl) {
        this.questionMapper = questionMapper;
        this.publicFrontendUrl = publicFrontendUrl;
    }

    public SurveyResponseDto mapEntityToResponseDto(Survey entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        return SurveyResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .publicId(entity.getPublicId())
                .publicUrl(publicFrontendUrl + "/survey/" + entity.getPublicId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .publishedAt(entity.getPublishedAt())
                .questions(entity.getQuestions().stream()
                        .sorted(Comparator.comparing(question -> question.getDisplayOrder()))
                        .map(questionMapper::mapEntityToResponseDto)
                        .toList())
                .build();
    }

    public Survey mapRequestDtoToEntity(SurveyRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("Dto cannot be null");
        return Survey.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(SurveyStatus.DRAFT)
                .publicId(UUID.randomUUID().toString().substring(0, 8))
                .build();
    }

    public void updateEntity(Survey entity, SurveyRequestDto dto) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        if (dto == null) throw new IllegalArgumentException("Dto cannot be null");
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
    }
}
