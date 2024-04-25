package dev.memocode.application.memo.dto.result;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class FindAllMyMemoVersion_MemoVersionResult {
    private UUID id;
    private String content;
    private Instant createdAt;
}
