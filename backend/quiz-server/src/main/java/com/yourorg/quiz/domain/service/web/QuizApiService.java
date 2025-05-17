package com.yourorg.quiz.domain.service.web;

import com.yourorg.quiz.adapter.in.web.dto.QuizResponseDto;
import com.yourorg.quiz.port.in.web.QuizApiPort;
import com.yourorg.quiz.port.out.persistence.QuizReadPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("quizApiService")
@Primary
@RequiredArgsConstructor
@Slf4j
public class QuizApiService implements QuizApiPort {

    private final QuizReadPort quizReadPort;

    @Override
    public Optional<QuizResponseDto> getQuizByCrawlingId(Long crawlingId) {
        log.info("📥퀴즈 조회 요청: crawlingId={}", crawlingId);
        return quizReadPort.QuizRequest(crawlingId)
            .map(entity -> new QuizResponseDto(
                entity.getCrawlingId(),
                entity.getQuizContent(),
                entity.getQuizAnswer()
            ));
    }
}