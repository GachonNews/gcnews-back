package com.yourorg.quiz.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.yourorg.quiz.adapter.out.repository.QuizJPARepository;
import com.yourorg.quiz.domain.entity.Quiz;
import com.yourorg.quiz.port.out.persistence.QuizReadPort;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizReadAdapter implements QuizReadPort {

    private final QuizJPARepository quizRepository;

    @Override
    public Optional<Quiz> QuizRequest(Long crawlingId) { // ✅ Optional 반환
        System.out.println("📥 전달 요청: " + crawlingId);
        return quizRepository.findByCrawlingId(crawlingId); // ✅ findByArticleId 메서드 사용
    }
}
