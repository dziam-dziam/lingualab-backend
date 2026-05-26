package com.lingualab.api.repository;

import com.lingualab.api.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, UUID> {
}
