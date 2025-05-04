package com.yourorg.crawling.adapter.out.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryRequestDto {
     private Long crawlingId;
     private String content;
}
