package com.lingualab.api.controller;

import com.lingualab.api.dto.survey.SurveyRequestDto;
import com.lingualab.api.dto.survey.SurveyResponseDto;
import com.lingualab.api.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/surveys")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping
    public SurveyResponseDto create(@Valid @RequestBody SurveyRequestDto request) {
        return surveyService.createSurvey(request);
    }

    @GetMapping
    public List<SurveyResponseDto> getAll() {
        return surveyService.getMySurveys();
    }

    @GetMapping("/{surveyId}")
    public SurveyResponseDto get(@PathVariable UUID surveyId) {
        return surveyService.getSurvey(surveyId);
    }

    @GetMapping("/public/{publicId}")
    public SurveyResponseDto getPublic(@PathVariable String publicId) {
        return surveyService.getPublicSurvey(publicId);
    }

    @PutMapping("/{surveyId}")
    public SurveyResponseDto update(@PathVariable UUID surveyId, @Valid @RequestBody SurveyRequestDto request) {
        return surveyService.updateSurvey(surveyId, request);
    }

    @PostMapping("/{surveyId}/publish")
    public SurveyResponseDto publish(@PathVariable UUID surveyId) {
        return surveyService.publishSurvey(surveyId);
    }

    @DeleteMapping("/{surveyId}")
    public void delete(@PathVariable UUID surveyId) {
        surveyService.deleteSurvey(surveyId);
    }
}
