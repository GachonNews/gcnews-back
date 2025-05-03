package com.yourorg.crawling.adapter.out.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequestDto {
     private Long newsId;
     private String content;
     private String title;
     private String category;
     private String date;
     private String newslink;
     private String imageUrl;
}
