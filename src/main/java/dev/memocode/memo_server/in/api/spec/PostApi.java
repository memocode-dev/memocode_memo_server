package dev.memocode.memo_server.in.api.spec;

import dev.memocode.memo_server.domain.memo.dto.response.PostAuthorDTO;
import dev.memocode.memo_server.domain.memo.dto.response.PostDetailDTO;
import dev.memocode.memo_server.domain.memo.dto.response.PostsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "post", description = "게시글 API")
@SecurityRequirement(name = "bearer-key")
public interface PostApi {

    @Operation(summary = "게시글 단일 조회")
    ResponseEntity<PostDetailDTO> findPost(UUID memoId);

    @Operation(summary = "게시글 전체 조회")
    ResponseEntity<Page<PostsDTO>> findAllPost(int page, int size);

    @Operation(summary = "해당 사용자에 대한 블로그 글 조회")
    ResponseEntity<Page<PostAuthorDTO>> findAllPostByAuthor(UUID authorId, int page, int size);
}
