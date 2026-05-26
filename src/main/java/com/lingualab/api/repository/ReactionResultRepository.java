package com.lingualab.api.repository;

import com.lingualab.api.entity.ReactionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReactionResultRepository extends JpaRepository<ReactionResult, UUID> {
}
