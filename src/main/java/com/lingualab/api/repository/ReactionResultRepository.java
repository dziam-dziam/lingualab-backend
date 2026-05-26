package com.lingualab.api.repository;

import com.lingualab.api.entity.ParticipantSession;
import com.lingualab.api.entity.Question;
import com.lingualab.api.entity.ReactionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReactionResultRepository extends JpaRepository<ReactionResult, UUID> {
    void deleteAllByQuestion(Question question);

    void deleteAllByQuestionIn(List<Question> questions);

    void deleteAllBySessionIn(List<ParticipantSession> sessions);
}