package dev.memocode.application.user;

import dev.memocode.domain.core.ForbiddenException;
import dev.memocode.domain.core.NotFoundException;
import dev.memocode.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static dev.memocode.application.user.AuthorApplicationErrorCode.AUTHOR_NOT_FOUND;
import static dev.memocode.domain.user.UserDomainErrorCode.DELETED_USER;

@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;

    public User findByIdEnabledUserElseThrow(UUID authorId) {
        User user = userRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(AUTHOR_NOT_FOUND));

        if (!user.getEnabled()) {
            throw new ForbiddenException(DELETED_USER);
        }

        return user;
    }

    public User findByUsernameElseThrow(String username) {
        // TODO 중복된 유저이름이면 어떻게 처리할 것인가?
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(AUTHOR_NOT_FOUND));

        if (!user.getEnabled()) {
            throw new ForbiddenException(DELETED_USER);
    }

            return user;
        }
}
