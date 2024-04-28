package dev.memocode.adapter.adapter_batch_memo.out.converter;

import dev.memocode.adapter.adapter_batch_core.MeilisearchUser;
import dev.memocode.adapter.adapter_batch_memo.out.dto.MeilisearchMemo;
import dev.memocode.domain.memo.Memo;
import dev.memocode.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class MeilisearchMemoConverter {

    public MeilisearchMemo toMeilisearchMemo(Memo memo) {
        User user = memo.getUser();
        MeilisearchUser meilisearchUser = MeilisearchUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .enabled(user.getEnabled())
                .build();

        return MeilisearchMemo.builder()
                .id(memo.getId())
                .title(memo.getTitle())
                .content(memo.getContent())
                .summary(memo.getSummary())
                .userId(user.getId())
                .user(meilisearchUser)
                .visibility(memo.getVisibility())
                .bookmarked(memo.getBookmarked())
                .security(memo.getSecurity())
                .createdAt(memo.getCreatedAt())
                .updatedAt(memo.getUpdatedAt())
                .deletedAt(memo.getDeletedAt())
                .deleted(memo.getDeleted())
                .build();
    }
}