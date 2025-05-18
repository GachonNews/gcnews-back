package com.yourorg.strike.adapter.in.dto;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private Long newsId;
    private LocalDate visitDate;
    private boolean isSummarized;
    private boolean isQuized;

    public DeliveryStrikeDto(Long newsId, LocalDate visitDate, boolean isSummarized, boolean isQuized) {
        this.newsId = newsId;
        this.visitDate = visitDate;
        this.isSummarized = isSummarized;
        this.isQuized = isQuized;
    }

    public static DeliveryStrikeDto fromEntity(Strike strike) {
        DeliveryStrikeDto dto = new DeliveryStrikeDto();
        dto.setNewsId(strike.getNewsId());
        dto.setVisitDate(strike.getVisitDate());
        dto.setSummarized(strike.isSummarized());
        dto.setQuized(strike.isQuized());
        return dto;
    }
}
