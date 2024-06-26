package dev.memocode.domain.memo;

import dev.memocode.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemoVersionDomainService {

    public MemoVersion createMemoVersion(Memo memo, User user) {
        user.assertIsEnabled();

        memo.assertIsNotDeleted();
        memo.assertIsMemoOwner(user);
        return memo.addVersion();
    }

    public void removeMemoVersion(Memo memo, User user, UUID memoVersionId) {
        user.assertIsEnabled();

        memo.assertIsNotDeleted();
        memo.assertIsMemoOwner(user);

        MemoVersion memoVersion = memo.findMemoVersionById(memoVersionId);
        memoVersion.assertIsMemoVersionOwner(user);

        memo.removeVersion(memoVersion);
    }

    public MemoVersion findMyMemoVersion(Memo memo, User user, UUID memoVersionId) {
        user.assertIsEnabled();

        memo.assertIsNotDeleted();
        memo.assertIsMemoOwner(user);

        MemoVersion memoVersion = memo.findMemoVersionById(memoVersionId);

        memoVersion.assertIsNotDeleted();
        memoVersion.assertIsMemoVersionOwner(user);

        return memoVersion;
    }

    public List<MemoVersion> findAllMyMemoVersion(Memo memo, User user) {
        user.assertIsEnabled();

        memo.assertIsNotDeleted();
        memo.assertIsMemoOwner(user);

        return memo.getMemoVersions().stream()
                .filter(memoVersion -> !memoVersion.getDeleted())
                .sorted(Comparator.comparingInt(MemoVersion::getVersion))
                .toList();
    }
}
