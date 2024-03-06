package dev.memocode.memo_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
    private String username;
    private String nickname;
    private UUID accountId;
}
