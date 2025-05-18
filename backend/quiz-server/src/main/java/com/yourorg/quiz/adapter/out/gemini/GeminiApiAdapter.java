package com.yourorg.quiz.adapter.out.gemini;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yourorg.quiz.port.out.gemini.GeminiApiPort;
import com.yourorg.quiz.domain.entity.Quiz;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GeminiApiAdapter implements GeminiApiPort {
    
    @Value("${gemini.api.key}")
    private String apiKey;
    
    @Value("${gemini.api.url}")
    private String apiUrl;
    
    @Value("${gemini.api.model}")
    private String model;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public GeminiApiAdapter(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public Quiz getQuizFromGpt(String content) {
        try {
            System.out.println("apiKey: " + apiKey);
            String fullUrl = apiUrl + "?key=" + apiKey;
            
            // 퀴즈 생성 프롬프트
            String instruction = """
                당신은 뉴스 기반 OX 퀴즈 생성 전문가입니다. 다음 뉴스 내용을 읽고:
                1. 뉴스와 관련된 OX 퀴즈 문제를 한 개 만들어주세요.
                2. 정답은 반드시 true 또는 false 중 하나여야 합니다.
                3. 다음 형식으로 출력해주세요:
                
                QUIZ_QUESTION: [퀴즈 문제]
                QUIZ_ANSWER: [true 또는 false]
                
                뉴스 내용:
                """;
            String fullContent = instruction + content;

            ObjectNode requestBody = objectMapper.createObjectNode();
            ArrayNode contentsArray = requestBody.putArray("contents");
            ObjectNode contentObj = contentsArray.addObject();
            
            ArrayNode partsArray = contentObj.putArray("parts"); 
            partsArray.addObject().put("text", fullContent);
            
            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), createHeaders());
            String response = restTemplate.postForObject(fullUrl, request, String.class);
            
            return parseQuiz(response, content);

        } catch (Exception e) {
            log.error("Gemini API 퀴즈 생성 오류: {}", e.getMessage());
            // 오류 시 기본 퀴즈 반환
            Quiz fallbackQuiz = new Quiz();
            fallbackQuiz.setQuizContent("API 오류로 퀴즈를 생성할 수 없습니다.");
            fallbackQuiz.setQuizAnswer(false);
            return fallbackQuiz;
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private Quiz parseQuiz(String response, String originalContent) {
        try {
            JsonNode jsonResponse = objectMapper.readTree(response);
            String generatedText = jsonResponse
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text").asText();
            
            // 퀴즈 문제와 답변 추출
            String quizText = "";
            Boolean quizAnswer = false;
            
            // QUIZ_QUESTION: 와 QUIZ_ANSWER: 태그를 찾아서 파싱
            String[] lines = generatedText.split("\n");
            for (String line : lines) {
                if (line.startsWith("QUIZ_QUESTION:")) {
                    quizText = line.substring("QUIZ_QUESTION:".length()).trim();
                } else if (line.startsWith("QUIZ_ANSWER:")) {
                    String answerText = line.substring("QUIZ_ANSWER:".length()).trim().toLowerCase();
                    quizAnswer = "true".equals(answerText);
                }
            }
            
            // Quiz 객체 생성 및 반환
            Quiz quiz = new Quiz();
            quiz.setContent(originalContent);
            quiz.setQuizContent(quizText);
            quiz.setQuizAnswer(quizAnswer);
            
            return quiz;
            
        } catch (Exception e) {
            log.error("퀴즈 응답 파싱 오류: {}", e.getMessage(), e);
            Quiz fallbackQuiz = new Quiz();
            fallbackQuiz.setQuizContent("파싱 오류로 퀴즈를 생성할 수 없습니다.");
            fallbackQuiz.setQuizAnswer(false);
            return fallbackQuiz;
        }
    }
}
