package com.yourorg.quiz.adapter.in.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizConsumerDto {
     private Long crawlingId;
     private String content;
}