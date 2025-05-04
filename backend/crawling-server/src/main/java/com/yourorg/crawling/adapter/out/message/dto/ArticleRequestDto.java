package com.yourorg.crawling.adapter.out.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto {
     private Long crawling_id;
     private String title;
     private String category;
     private String content;
     private String articleLink;
     private String imgLink;
     private String uploadAt;
}
