package dev.memocode.memo_server.usecase;

import com.meilisearch.sdk.model.SearchResultPaginated;
import dev.memocode.memo_server.domain.memo.dto.MemoSearchRequestDTO;
import dev.memocode.memo_server.domain.memo.dto.request.MemoCreateDTO;
import dev.memocode.memo_server.domain.memo.dto.request.MemoDeleteDTO;
import dev.memocode.memo_server.domain.memo.dto.request.MemoUpdateDTO;
import dev.memocode.memo_server.domain.memo.dto.response.MemoDetailDTO;
import dev.memocode.memo_server.domain.memo.dto.response.MemosBookmarkedDTO;
import dev.memocode.memo_server.domain.memo.dto.response.MemosDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public interface MemoUseCase {

    UUID createMemo(@Valid MemoCreateDTO dto);

    void deleteMemo(@Valid MemoDeleteDTO dto);

    void updateMemo(MemoUpdateDTO dto);

    MemoDetailDTO findMemo(UUID memoId, UUID authorId);

    MemosDTO findMemos(UUID authorId);

    MemosBookmarkedDTO findBookmarkedMemos(UUID authorId);

    SearchResultPaginated searchMemos(MemoSearchRequestDTO dto);
}
