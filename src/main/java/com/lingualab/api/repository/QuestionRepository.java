package com.lingualab.api.repository;

import com.lingualab.api.entity.Question;
import com.lingualab.api.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllBySurveyOrderByDisplayOrderAsc(Survey survey);
}
