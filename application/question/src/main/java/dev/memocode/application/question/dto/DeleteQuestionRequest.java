package dev.memocode.application.question.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeleteQuestionRequest {
    private UUID questionId;
    private UUID userId;
}
