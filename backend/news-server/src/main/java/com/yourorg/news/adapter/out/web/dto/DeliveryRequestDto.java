package com.yourorg.news.adapter.out.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDto {
     private Long newsId;
     private String content;
}
