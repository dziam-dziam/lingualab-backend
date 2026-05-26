package com.lingualab.api.repository;

import com.lingualab.api.entity.Survey;
import com.lingualab.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SurveyRepository extends JpaRepository<Survey, UUID> {
    List<Survey> findAllByOwnerOrderByCreatedAtDesc(User owner);
    Optional<Survey> findByPublicId(String publicId);
}
