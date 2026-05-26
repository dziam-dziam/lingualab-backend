package com.lingualab.api.mapper;

import com.lingualab.api.dto.response.AnswerResponseDto;
import com.lingualab.api.dto.response.SubmissionResponseDto;
import com.lingualab.api.entity.ParticipantSession;
import com.lingualab.api.entity.SurveyResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Builder
@AllArgsConstructor
public class ResponseMapper {

    public AnswerResponseDto mapEntityToAnswerDto(SurveyResponse entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        return AnswerResponseDto.builder()
                .id(entity.getId())
                .questionId(entity.getQuestion().getId())
                .questionTitle(entity.getQuestion().getTitle())
                .answerText(entity.getAnswerText())
                .selectedOption(entity.getSelectedOption())
                .reactionTimeMs(entity.getReactionTimeMs())
                .submittedAt(entity.getSubmittedAt())
                .build();
    }

    public SubmissionResponseDto mapSessionToSubmissionDto(ParticipantSession session, List<SurveyResponse> answers) {
        if (session == null) throw new IllegalArgumentException("Session cannot be null");
        return SubmissionResponseDto.builder()
                .sessionId(session.getId())
                .startedAt(session.getStartedAt())
                .completedAt(session.getCompletedAt())
                .answers(answers.stream().map(this::mapEntityToAnswerDto).toList())
                .build();
    }
}
