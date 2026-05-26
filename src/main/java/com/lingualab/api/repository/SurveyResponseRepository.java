package com.lingualab.api.repository;

import com.lingualab.api.entity.ParticipantSession;
import com.lingualab.api.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, UUID> {
    List<SurveyResponse> findAllBySession(ParticipantSession session);
}
