package dev.memocode.application.question.converter;

import dev.memocode.application.question.dto.FindQuestion_QuestionResult;
import dev.memocode.application.question.dto.FindQuestion_UserResult;
import dev.memocode.application.question.dto.SearchQuestion_QuestionResult;
import dev.memocode.application.question.dto.SearchQuestion_UserResult;
import dev.memocode.domain.question.Question;
import dev.memocode.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionDTOConverter {
    
    public FindQuestion_QuestionResult toFindQuestion_QuestionResult(Question question) {
        return FindQuestion_QuestionResult.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .user(toFindQuestion_UserResult(question.getUser()))
                .build();
    }

    public FindQuestion_UserResult toFindQuestion_UserResult(User user) {
        return FindQuestion_UserResult.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .build();
    }

    public List<SearchQuestion_QuestionResult> toSearchQuestion_QuestionResult(List<Question> questions) {
        return questions.stream()
                .map(this::toSearchQuestion_QuestionResult)
                .toList();
    }

    public SearchQuestion_UserResult toSearchQuestion_UserResult(User user) {
        return SearchQuestion_UserResult.builder()
                .id(user.getId())
                .build();
    }

    public SearchQuestion_QuestionResult toSearchQuestion_QuestionResult(Question question) {
        return SearchQuestion_QuestionResult.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .user(toSearchQuestion_UserResult(question.getUser()))
                .build();
    }
}
