package com.lingualab.api.mapper;

import com.lingualab.api.dto.question.QuestionOptionDto;
import com.lingualab.api.dto.question.QuestionRequestDto;
import com.lingualab.api.dto.question.QuestionResponseDto;
import com.lingualab.api.entity.Question;
import com.lingualab.api.entity.QuestionOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@Builder
@AllArgsConstructor
public class QuestionMapper {

    public QuestionResponseDto mapEntityToResponseDto(Question entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        return QuestionResponseDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .placeholder(entity.getPlaceholder())
                .imageUrl(entity.getImageUrl())
                .imageKey(entity.getImageKey())
                .stimulus(entity.getStimulus())
                .allowedKeys(entity.getAllowedKeys())
                .delayMs(entity.getDelayMs())
                .displayOrder(entity.getDisplayOrder())
                .options(mapOptionsToDto(entity.getOptions()))
                .build();
    }

    public Question mapRequestDtoToEntity(QuestionRequestDto dto) {
        if (dto == null) throw new IllegalArgumentException("Dto cannot be null");
        Question question = Question.builder()
                .type(dto.getType())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .placeholder(dto.getPlaceholder())
                .imageUrl(dto.getImageUrl())
                .imageKey(dto.getImageKey())
                .stimulus(dto.getStimulus())
                .allowedKeys(dto.getAllowedKeys())
                .delayMs(dto.getDelayMs())
                .displayOrder(dto.getDisplayOrder())
                .build();
        question.setOptions(mapOptionsToEntity(dto.getOptions(), question));
        return question;
    }

    public void updateEntity(Question entity, QuestionRequestDto dto) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        if (dto == null) throw new IllegalArgumentException("Dto cannot be null");
        entity.setType(dto.getType());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPlaceholder(dto.getPlaceholder());
        entity.setImageUrl(dto.getImageUrl());
        entity.setImageKey(dto.getImageKey());
        entity.setStimulus(dto.getStimulus());
        entity.setAllowedKeys(dto.getAllowedKeys());
        entity.setDelayMs(dto.getDelayMs());
        entity.setDisplayOrder(dto.getDisplayOrder());
        entity.getOptions().clear();
        entity.getOptions().addAll(mapOptionsToEntity(dto.getOptions(), entity));
    }

    public List<QuestionOptionDto> mapOptionsToDto(List<QuestionOption> options) {
        if (options == null) return List.of();
        return options.stream()
                .sorted(Comparator.comparing(QuestionOption::getDisplayOrder))
                .map(option -> QuestionOptionDto.builder()
                        .id(option.getId())
                        .label(option.getLabel())
                        .value(option.getValue())
                        .displayOrder(option.getDisplayOrder())
                        .build())
                .toList();
    }

    private List<QuestionOption> mapOptionsToEntity(List<QuestionOptionDto> options, Question question) {
        if (options == null) return List.of();
        return options.stream()
                .map(option -> QuestionOption.builder()
                        .id(option.getId())
                        .label(option.getLabel())
                        .value(option.getValue())
                        .displayOrder(option.getDisplayOrder())
                        .question(question)
                        .build())
                .toList();
    }
}
