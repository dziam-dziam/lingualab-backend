package com.lingualab.api.repository;

import com.lingualab.api.entity.ParticipantSession;
import com.lingualab.api.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantSessionRepository extends JpaRepository<ParticipantSession, UUID> {
    List<ParticipantSession> findAllBySurveyOrderByStartedAtDesc(Survey survey);

    void deleteAllBySurvey(Survey survey);
}