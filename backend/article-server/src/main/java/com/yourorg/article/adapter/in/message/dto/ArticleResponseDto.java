package com.yourorg.article.adapter.in.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
     private Long crawlingId;
     private String title;
     private String category;
     private String content;
     private String articleLink;
     private String imgLink;
     private String uploadAt;
}
