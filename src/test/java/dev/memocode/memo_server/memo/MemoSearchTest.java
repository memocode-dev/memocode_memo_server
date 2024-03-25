package dev.memocode.memo_server.memo;

import dev.memocode.memo_server.base.BaseTest;
import dev.memocode.memo_server.domain.memo.dto.request.MemoCreateDTO;
import dev.memocode.memo_server.usecase.MemoUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemoSearchTest extends BaseTest {

    @Autowired
    private MemoUseCase memoUseCase;

    @Test
    @DisplayName("title을 기준으로 `메모코드`를 검색하여 하나의 데이터를 조회되게 하기")
    void test1() {
        MemoCreateDTO memoCreateDTO = MemoCreateDTO.builder()
                .title("메모코드")
                .content("메모코드")
                .summary("요약")
                .authorId(author.getId())
                .build();

        memoUseCase.createMemo(memoCreateDTO);
    }
}