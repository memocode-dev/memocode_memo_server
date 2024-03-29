package dev.memocode.memo_server.domain.memo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoUpdateDTO {

    private UUID memoId;
    private UUID authorId;
    private String title;
    private String content;
    private String summary;
    private Boolean visibility;
    private Boolean security;
    private Boolean bookmarked;
}
