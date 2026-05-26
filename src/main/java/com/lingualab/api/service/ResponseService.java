package com.lingualab.api.service;

import com.lingualab.api.dto.response.AnswerRequestDto;
import com.lingualab.api.dto.response.ResponseSubmitRequestDto;
import com.lingualab.api.dto.response.SubmissionResponseDto;
import com.lingualab.api.entity.ParticipantSession;
import com.lingualab.api.entity.Question;
import com.lingualab.api.entity.QuestionType;
import com.lingualab.api.entity.ReactionResult;
import com.lingualab.api.entity.Survey;
import com.lingualab.api.entity.SurveyResponse;
import com.lingualab.api.entity.SurveyStatus;
import com.lingualab.api.exception.ResourceNotFoundException;
import com.lingualab.api.mapper.ResponseMapper;
import com.lingualab.api.repository.ParticipantSessionRepository;
import com.lingualab.api.repository.QuestionRepository;
import com.lingualab.api.repository.ReactionResultRepository;
import com.lingualab.api.repository.SurveyRepository;
import com.lingualab.api.repository.SurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ParticipantSessionRepository sessionRepository;
    private final SurveyResponseRepository responseRepository;
    private final ReactionResultRepository reactionResultRepository;
    private final ResponseMapper responseMapper;
    private final SurveyService surveyService;

    @Transactional
    public SubmissionResponseDto submitPublicResponses(String publicId, ResponseSubmitRequestDto request) {
        Survey survey = surveyRepository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found"));
        if (survey.getStatus() != SurveyStatus.PUBLISHED) {
            throw new ResourceNotFoundException("Survey not found");
        }

        ParticipantSession session = request.getSessionId() == null
                ? createSession(survey, request.getUserAgent())
                : sessionRepository.findById(request.getSessionId()).orElseGet(() -> createSession(survey, request.getUserAgent()));

        List<SurveyResponse> savedAnswers = request.getAnswers().stream()
                .map(answer -> createAnswer(session, answer))
                .map(responseRepository::save)
                .toList();
        session.setCompletedAt(Instant.now());
        sessionRepository.save(session);
        return responseMapper.mapSessionToSubmissionDto(session, savedAnswers);
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponseDto> getResponses(UUID surveyId) {
        Survey survey = surveyService.getOwnedSurvey(surveyId);
        return sessionRepository.findAllBySurveyOrderByStartedAtDesc(survey).stream()
                .map(session -> responseMapper.mapSessionToSubmissionDto(session, responseRepository.findAllBySession(session)))
                .toList();
    }

    private ParticipantSession createSession(Survey survey, String userAgent) {
        return sessionRepository.save(ParticipantSession.builder()
                .survey(survey)
                .startedAt(Instant.now())
                .userAgent(userAgent)
                .build());
    }

    private SurveyResponse createAnswer(ParticipantSession session, AnswerRequestDto answer) {
        Question question = questionRepository.findById(answer.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        SurveyResponse response = SurveyResponse.builder()
                .session(session)
                .question(question)
                .answerText(answer.getAnswerText())
                .selectedOption(answer.getSelectedOption())
                .reactionTimeMs(answer.getReactionTimeMs())
                .submittedAt(Instant.now())
                .build();
        if (question.getType() == QuestionType.REACTION_TIME) {
            reactionResultRepository.save(ReactionResult.builder()
                    .session(session)
                    .question(question)
                    .stimulus(question.getStimulus())
                    .pressedKey(answer.getPressedKey())
                    .reactionTimeMs(answer.getReactionTimeMs())
                    .recordedAt(Instant.now())
                    .build());
        }
        return response;
    }
}
