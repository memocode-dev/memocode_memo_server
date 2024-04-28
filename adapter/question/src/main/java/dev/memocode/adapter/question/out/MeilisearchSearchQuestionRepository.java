package dev.memocode.adapter.question.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import dev.memocode.adapter.adapter_meilisearch_core.MeilisearchSearchResponse;
import dev.memocode.application.question.repository.SearchQuestionRepository;
import dev.memocode.domain.core.InternalServerException;
import dev.memocode.domain.core.ValidationException;
import dev.memocode.domain.question.immutable.ImmutableQuestion;
import dev.memocode.domain.user.ImmutableUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dev.memocode.adapter.adapter_meilisearch_core.AdapterMeilisearchErrorCode.*;

@Repository
@RequiredArgsConstructor
public class MeilisearchSearchQuestionRepository implements SearchQuestionRepository {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

    private final Client client;

    @Value("${custom.meilisearch.index.questions}")
    private String meilisearchIndexMemos;

    private final static String[] attributesToRetrieve =
            {"id", "title", "content", "tags", "user", "createdAt", "updatedAt"};
    private final static String[] attributesToHighlight = {"title", "content", "tags"};
    private final static String[] attributesToCrop = {"content"};
    private final static String[] sort = new String[] {"updatedAt:desc"};
    private final static int cropLength = 50;

    @Override
    public Page<ImmutableQuestion> searchQuestion(String keyword, int page, int pageSize) {
        try {
            SearchRequest request = createSearchQuestionRequest(keyword, page, pageSize);

            Index index = client.getIndex(meilisearchIndexMemos);

            String rawJson = index.rawSearch(request);

            TypeReference<MeilisearchSearchResponse<MeilisearchSearchQuestion_QuestionResult>> typeRef =
                    new TypeReference<>() {};
            return toEntity(objectMapper.readValue(rawJson, typeRef));
        } catch (JsonProcessingException e) {
            throw new InternalServerException(MEILISEARCH_PARSING_ERROR, e);
        } catch (Exception e) {
            throw new InternalServerException(MEILISEARCH_SEARCH_ERROR, e);
        }
    }

    private SearchRequest createSearchQuestionRequest(String keyword, int page, int pageSize) {
        if (page < 0) {
            throw new ValidationException(MEILISEARCH_INVALID_PAGE_NUMBER);
        }

        return new SearchRequest(keyword)
                .setFilter(new String[]{
                        "deleted = false",
                })
                .setSort(sort)
                .setAttributesToRetrieve(attributesToRetrieve)
                .setAttributesToHighlight(attributesToHighlight)
                .setAttributesToCrop(attributesToCrop)
                .setCropLength(cropLength)
                .setPage(page + 1)
                .setHitsPerPage(pageSize);
    }

    private Page<ImmutableQuestion> toEntity(MeilisearchSearchResponse<MeilisearchSearchQuestion_QuestionResult> meilisearchSearchResponse) {

        List<MeilisearchSearchQuestion_QuestionResult> content = meilisearchSearchResponse.getContent();

        List<ImmutableQuestion> immutableQuestions = content.stream().map(item -> {
                    MeilisearchSearchQuestion_UserResult meilisearhUser = item.getUser();

                    ImmutableUser user = ImmutableUser.builder()
                            .id(meilisearhUser.getId())
                            .username(meilisearhUser.getUsername())
                            .enabled(meilisearhUser.getEnabled())
                            .build();

                    MeilisearchSearchQuestion_QuestionResult formatted = item.get_formatted();

                    MeilisearchSearchQuestion_UserResult formattedMeilisearchUser = formatted.getUser();

                    ImmutableUser formattedUser = ImmutableUser.builder()
                            .id(formattedMeilisearchUser.getId())
                            .username(formattedMeilisearchUser.getUsername())
                            .enabled(formattedMeilisearchUser.getEnabled())
                            .build();

                    ImmutableQuestion formattedQuestion = ImmutableQuestion.builder()
                            .id(formatted.getId())
                            .title(formatted.getTitle())
                            .content(formatted.getContent())
                            .tags(formatted.getTags())
                            .user(formattedUser)
                            .createdAt(formatted.getCreatedAt())
                            .updatedAt(formatted.getUpdatedAt())
                            .deleted(formatted.getDeleted())
                            .deletedAt(formatted.getDeletedAt())
                            .build();

                    return ImmutableQuestion.builder()
                            .id(item.getId())
                            .title(item.getTitle())
                            .content(item.getContent())
                            .tags(item.getTags())
                            .user(user)
                            .formattedQuestion(formattedQuestion)
                            .createdAt(item.getCreatedAt())
                            .updatedAt(item.getUpdatedAt())
                            .deleted(item.getDeleted())
                            .deletedAt(item.getDeletedAt())
                            .build();
                })
                .toList();

        Pageable pageable = PageRequest.of(
                meilisearchSearchResponse.getPage() - 1, meilisearchSearchResponse.getHitsPerPage());
        return PageableExecutionUtils.getPage(immutableQuestions, pageable, meilisearchSearchResponse::getTotalHits);
    }
}
