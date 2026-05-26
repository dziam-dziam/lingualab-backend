package com.lingualab.api.controller;

import com.lingualab.api.dto.question.QuestionRequestDto;
import com.lingualab.api.dto.question.QuestionResponseDto;
import com.lingualab.api.dto.question.ReorderQuestionDto;
import com.lingualab.api.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/survey/{surveyId}")
    public QuestionResponseDto add(@PathVariable UUID surveyId, @Valid @RequestBody QuestionRequestDto request) {
        return questionService.addQuestion(surveyId, request);
    }

    @PutMapping("/{questionId}")
    public QuestionResponseDto update(@PathVariable UUID questionId, @Valid @RequestBody QuestionRequestDto request) {
        return questionService.updateQuestion(questionId, request);
    }

    @DeleteMapping("/{questionId}")
    public void delete(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
    }

    @PutMapping("/survey/{surveyId}/order")
    public List<QuestionResponseDto> reorder(
            @PathVariable UUID surveyId,
            @Valid @RequestBody List<ReorderQuestionDto> request
    ) {
        return questionService.reorderQuestions(surveyId, request);
    }
}