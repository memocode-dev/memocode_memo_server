package dev.memocode.domain.memo;

import dev.memocode.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class MemoDomainService {
    private final static Boolean DEFAULT_BOOKMARKED = false;
    private final static Boolean DEFAULT_VISIBILITY = false;

    public Memo createMemo(@Valid MemoCreateDomainDTO dto) {
        User user = dto.getUser();

        user.assertIsEnabled();

        return Memo.builder()
                .id(UUID.randomUUID())
                .title(dto.getTitle())
                .content(dto.getContent())
                .summary(dto.getSummary())
                .visibility(DEFAULT_VISIBILITY)
                .security(dto.getSecurity())
                .bookmarked(DEFAULT_BOOKMARKED)
                .user(user)
                .build();
    }

    public void updateMemo(Memo memo, User user, @Valid MemoUpdateDomainDTO dto) {
        user.assertIsEnabled();

        memo.assertIsNotDeleted();
        memo.assertIsMemoOwner(user);
        memo.change(dto);
    }

    public void softDeleteMemo(Memo memo, User user) {
        memo.assertIsNotDeleted();
        memo.assertIsMemoOwner(user);
        memo.softDelete();
    }

    public Memo findMyMemo(Memo memo, User user) {
        memo.assertIsNotDeleted();
        memo.assertIsMemoOwner(user);
        return memo;
    }

    public List<Memo> findAllMyMemo(List<Memo> memos, User user) {
        return memos.stream()
                .filter(memo -> !memo.isDeleted() && memo.isMemoOwner(user))
                .toList();
    }

    public List<Memo> searchMyMemo(List<Memo> memos, User user) {
        return memos.stream()
                .filter(memo -> !memo.isDeleted() && memo.isMemoOwner(user) && memo.getFormattedMemo() != null)
                .toList();
    }

    public Memo findMemo(Memo memo) {
        memo.assertIsNotDeleted();
        memo.assertIsVisibility();
        return memo;
    }

    public List<Memo> searchMemo(List<Memo> memos) {
        return memos.stream()
                .filter(memo -> !memo.isDeleted() && memo.isVisibility())
                .toList();
    }
}
