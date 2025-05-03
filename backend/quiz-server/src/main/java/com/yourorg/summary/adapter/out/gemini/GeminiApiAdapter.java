package com.yourorg.summary.adapter.out.gemini;

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
import com.yourorg.summary.port.out.gemini.GeminiApiPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GeminiApiAdapter implements GeminiApiPort {
    
    @Value("${gemini.api.key:AIzaSyBKwtRlVcgFFlSzjSnl_2B-7dD1HhnnH4A}")
    private String apiKey;
    
    
    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro-latest:generateContent}")
    private String apiUrl;
    
    @Value("${gemini.api.model:gemini-1.5-pro-latest}")
    private String model; // ✅ 공식 모델명 사용
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public GeminiApiAdapter(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public String getSummaryFromGpt(String content) {
        try {
            System.out.println("apiKey: " + apiKey);
            String fullUrl = apiUrl + "?key=" + apiKey;
            
                // ▶▶▶▶▶ 추가: 요청 지시사항 프롬프트에 포함 ◀◀◀◀◀
            String instruction = """
                당신은 뉴스 요약 장인입니다. 다음 뉴스를 반드시 세 줄로 요약하고, 
                마지막에 '핵심 한 문장:'으로 시작하는 키워드 문장을 추가해주세요.
                그리고 한 줄마다 엔터를 넣어주세요.
                뉴스 내용:
                """;
            String fullContent = instruction + content;

            // 수정된 부분: 타입 변환 오류 해결
            ObjectNode requestBody = objectMapper.createObjectNode();
            ArrayNode contentsArray = requestBody.putArray("contents");
            ObjectNode contentObj = contentsArray.addObject(); // 반환값을 올바른 변수에 할당
            
            // 수정된 부분: ArrayNode 타입으로 변수 선언
            ArrayNode partsArray = contentObj.putArray("parts"); 
            partsArray.addObject().put("text", fullContent);
            
            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), createHeaders());
            
            String response = restTemplate.postForObject(fullUrl, request, String.class);
            
            return parseSummary(response);

        } catch (Exception e) {
            log.error("Gemini API 오류: {}", e, e);
            return "제미나이 연동 실패: " + e.getMessage();
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String parseSummary(String response) {
        try {
            JsonNode jsonResponse = objectMapper.readTree(response);
            
            // ✅ Gemini 응답 구조 파싱 (https://cloud.google.com/vertex-ai/docs/chat/generative 참조)
            return jsonResponse
                .path("candidates")
                .path(0)
                .path("content")
                .path("parts")
                .path(0)
                .path("text").asText();
            
        } catch (Exception e) {
            throw new RuntimeException("응답 파싱 오류: " + e.getMessage(), e);
        }
    }
}
