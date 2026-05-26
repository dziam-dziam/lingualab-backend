package com.lingualab.api.validator;

import com.lingualab.api.entity.Question;
import com.lingualab.api.entity.QuestionType;
import org.springframework.stereotype.Component;

@Component
public class SurveyValidator {

    public void validateQuestion(Question question) {
        if (question.getType() == QuestionType.MULTIPLE_CHOICE && question.getOptions().isEmpty()) {
            throw new IllegalArgumentException("Multiple choice questions require at least one option");
        }

        if (question.getType() == QuestionType.REACTION_TIME && question.getDelayMs() == null) {
            throw new IllegalArgumentException("Reaction time questions require a delay");
        }
    }
}