package com.yourorg.summary.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryConsumerDto {
    private Long crawlingId;
    private String content;
}
