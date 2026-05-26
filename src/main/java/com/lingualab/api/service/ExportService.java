package com.lingualab.api.service;

import com.lingualab.api.dto.response.SubmissionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExportService {
    private final ResponseService responseService;

    public byte[] exportCsv(UUID surveyId) {
        StringBuilder csv = new StringBuilder("sessionId,completedAt,questionId,questionTitle,answerText,selectedOption,reactionTimeMs\n");
        for (SubmissionResponseDto submission : responseService.getResponses(surveyId)) {
            submission.getAnswers().forEach(answer -> csv
                    .append(escape(submission.getSessionId().toString())).append(',')
                    .append(escape(String.valueOf(submission.getCompletedAt()))).append(',')
                    .append(escape(answer.getQuestionId().toString())).append(',')
                    .append(escape(answer.getQuestionTitle())).append(',')
                    .append(escape(answer.getAnswerText())).append(',')
                    .append(escape(answer.getSelectedOption())).append(',')
                    .append(answer.getReactionTimeMs() == null ? "" : answer.getReactionTimeMs())
                    .append('\n'));
        }
        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String escape(String value) {
        if (value == null || "null".equals(value)) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
