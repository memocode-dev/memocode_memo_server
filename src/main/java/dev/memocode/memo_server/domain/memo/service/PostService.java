package dev.memocode.memo_server.domain.memo.service;

import dev.memocode.memo_server.domain.memo.entity.Memo;
import dev.memocode.memo_server.domain.memo.entity.MemoVersion;
import dev.memocode.memo_server.domain.memo.entity.SelectedMemoVersion;
import dev.memocode.memo_server.domain.memo.repository.PostRepository;
import dev.memocode.memo_server.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static dev.memocode.memo_server.exception.GlobalErrorCode.NOT_VALID_MEMO_OWNER;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public SelectedMemoVersion createPost(Memo memo, MemoVersion memoVersion, UUID accountId) {
        validOwner(memo.getAuthor().getAccountId(), accountId);

        SelectedMemoVersion post = SelectedMemoVersion.builder()
                .memo(memo)
                .memoVersion(memoVersion)
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Memo memo, MemoVersion memoVersion, UUID accountId) {
        validOwner(memo.getAuthor().getAccountId(), accountId);
        postRepository.deleteByMemoAndMemoVersion(memo, memoVersion);
    }

    private void validOwner(UUID ownerAccountId, UUID accountId) {
        if (!ownerAccountId.equals(accountId)){
            throw new GlobalException(NOT_VALID_MEMO_OWNER);
        }
    }
}
