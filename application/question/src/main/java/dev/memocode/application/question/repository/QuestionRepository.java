package dev.memocode.application.question.repository;

import dev.memocode.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
