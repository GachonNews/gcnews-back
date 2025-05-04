package com.yourorg.article.adapter.out.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDto {
     private Long articleId;
     private String title;
     private String category;
     private String content;
     private String articleLink;
     private String imgLink;
     private String uploadAt;
}
