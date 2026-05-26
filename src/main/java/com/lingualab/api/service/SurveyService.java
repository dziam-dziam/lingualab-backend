package com.lingualab.api.service;

import com.lingualab.api.dto.survey.SurveyRequestDto;
import com.lingualab.api.dto.survey.SurveyResponseDto;
import com.lingualab.api.entity.Survey;
import com.lingualab.api.entity.SurveyStatus;
import com.lingualab.api.entity.User;
import com.lingualab.api.exception.ForbiddenOperationException;
import com.lingualab.api.exception.ResourceNotFoundException;
import com.lingualab.api.mapper.SurveyMapper;
import com.lingualab.api.repository.SurveyRepository;
import com.lingualab.api.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;
    private final CurrentUserService currentUserService;

    @Transactional
    public SurveyResponseDto createSurvey(SurveyRequestDto request) {
        User owner = currentUserService.getCurrentUser();
        Survey survey = surveyMapper.mapRequestDtoToEntity(request);
        survey.setOwner(owner);
        survey.setCreatedAt(Instant.now());
        survey.setUpdatedAt(Instant.now());
        return surveyMapper.mapEntityToResponseDto(surveyRepository.save(survey));
    }

    @Transactional(readOnly = true)
    public List<SurveyResponseDto> getMySurveys() {
        User owner = currentUserService.getCurrentUser();
        return surveyRepository.findAllByOwnerOrderByCreatedAtDesc(owner).stream()
                .map(surveyMapper::mapEntityToResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SurveyResponseDto getSurvey(UUID surveyId) {
        Survey survey = getOwnedSurvey(surveyId);
        return surveyMapper.mapEntityToResponseDto(survey);
    }

    @Transactional(readOnly = true)
    public SurveyResponseDto getPublicSurvey(String publicId) {
        Survey survey = surveyRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
        if (survey.getStatus() != SurveyStatus.PUBLISHED) {
            throw new ResourceNotFoundException("Survey not found");
        }
        return surveyMapper.mapEntityToResponseDto(survey);
    }

    @Transactional
    public SurveyResponseDto updateSurvey(UUID surveyId, SurveyRequestDto request) {
        Survey survey = getOwnedSurvey(surveyId);
        surveyMapper.updateEntity(survey, request);
        survey.setUpdatedAt(Instant.now());
        return surveyMapper.mapEntityToResponseDto(surveyRepository.save(survey));
    }

    @Transactional
    public void deleteSurvey(UUID surveyId) {
        Survey survey = getOwnedSurvey(surveyId);
        surveyRepository.delete(survey);
    }

    @Transactional
    public SurveyResponseDto publishSurvey(UUID surveyId) {
        Survey survey = getOwnedSurvey(surveyId);
        if (survey.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Survey must contain at least one question before publishing");
        }
        survey.setStatus(SurveyStatus.PUBLISHED);
        survey.setPublishedAt(Instant.now());
        survey.setUpdatedAt(Instant.now());
        return surveyMapper.mapEntityToResponseDto(surveyRepository.save(survey));
    }

    public Survey getOwnedSurvey(UUID surveyId) {
        User owner = currentUserService.getCurrentUser();
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
        if (!survey.getOwner().getId().equals(owner.getId())) {
            throw new ForbiddenOperationException("Survey belongs to another researcher");
        }
        return survey;
    }
}
