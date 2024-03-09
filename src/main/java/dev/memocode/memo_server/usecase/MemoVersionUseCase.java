package dev.memocode.memo_server.usecase;

import dev.memocode.memo_server.domain.memo.dto.request.MemoVersionCreateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface MemoVersionUseCase {

    @Transactional
    UUID createMemoVersion(MemoVersionCreateDTO dto);
}
