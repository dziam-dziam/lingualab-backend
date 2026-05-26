package com.lingualab.api.service;

import com.lingualab.api.dto.question.QuestionRequestDto;
import com.lingualab.api.dto.question.QuestionResponseDto;
import com.lingualab.api.dto.question.ReorderQuestionDto;
import com.lingualab.api.entity.Question;
import com.lingualab.api.entity.Survey;
import com.lingualab.api.exception.ResourceNotFoundException;
import com.lingualab.api.mapper.QuestionMapper;
import com.lingualab.api.repository.QuestionRepository;
import com.lingualab.api.repository.SurveyRepository;
import com.lingualab.api.validator.SurveyValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final SurveyService surveyService;
    private final QuestionMapper questionMapper;
    private final SurveyValidator surveyValidator;
    private final SurveyRepository surveyRepository; // if needed for updatedAt persist


    @Transactional
    public QuestionResponseDto addQuestion(UUID surveyId, QuestionRequestDto request) {
        Survey survey = surveyService.getOwnedSurvey(surveyId);
        Question question = questionMapper.mapRequestDtoToEntity(request);
        question.setSurvey(survey);
        question.getOptions().forEach(option -> option.setQuestion(question));
        surveyValidator.validateQuestion(question);
        survey.setUpdatedAt(Instant.now());
        return questionMapper.mapEntityToResponseDto(questionRepository.save(question));
    }

    @Transactional
    public QuestionResponseDto updateQuestion(UUID questionId, QuestionRequestDto request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        surveyService.getOwnedSurvey(question.getSurvey().getId());
        questionMapper.updateEntity(question, request);
        question.getOptions().forEach(option -> option.setQuestion(question));
        surveyValidator.validateQuestion(question);
        question.getSurvey().setUpdatedAt(Instant.now());
        return questionMapper.mapEntityToResponseDto(questionRepository.save(question));
    }

    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        // verify ownership: will throw if not owned by current user
        UUID surveyId = question.getSurvey().getId();
        Survey owned = surveyService.getOwnedSurvey(surveyId);

        // remove the question
        questionRepository.delete(question);

        // update survey.updatedAt
        owned.setUpdatedAt(Instant.now());
        surveyRepository.save(owned);
    }

    @Transactional
    public List<QuestionResponseDto> reorderQuestions(UUID surveyId, List<ReorderQuestionDto> request) {
        Survey survey = surveyService.getOwnedSurvey(surveyId);
        List<Question> questions = questionRepository.findAllBySurveyOrderByDisplayOrderAsc(survey);
        request.forEach(item -> questions.stream()
                .filter(question -> question.getId().equals(item.getQuestionId()))
                .findFirst()
                .ifPresent(question -> question.setDisplayOrder(item.getDisplayOrder())));
        survey.setUpdatedAt(Instant.now());
        return questionRepository.saveAll(questions).stream()
                .sorted(Comparator.comparing(Question::getDisplayOrder))
                .map(questionMapper::mapEntityToResponseDto)
                .toList();
    }
}