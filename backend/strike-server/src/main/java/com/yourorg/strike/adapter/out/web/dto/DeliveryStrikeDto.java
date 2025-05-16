package com.yourorg.strike.adapter.out.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yourorg.strike.domain.entity.Strike;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class DeliveryStrikeDto {

    private Long userId;
    private Long newsId;
    private LocalDate visitDate;
    private boolean isSummarized;
    private boolean isQuized;

    public DeliveryStrikeDto(Long userId, Long newsId, LocalDate visitDate, boolean isSummarized, boolean isQuized) {
        this.userId = userId;
        this.newsId = newsId;
        this.visitDate = visitDate;
        this.isSummarized = isSummarized;
        this.isQuized = isQuized;
    }

 @JsonProperty("id")
    private void unpackNestedId(Map<String,Object> id) {
        this.userId    = ((Number) id.get("userId")).longValue();
        this.visitDate = LocalDate.parse((String) id.get("dateTime"));
    }

    public static DeliveryStrikeDto fromEntity(Strike strike) {
        DeliveryStrikeDto dto = new DeliveryStrikeDto();
        dto.setUserId(strike.getUserId());
        dto.setNewsId(strike.getNewsId());
        dto.setVisitDate(strike.getVisitDate());
        dto.setSummarized(strike.isSummarized());
        dto.setQuized(strike.isQuized());
        // Map other fields as necessary
        return dto;
    }
}
