package com.yourorg.strike.domain.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@IdClass(StrikeMapping.class)
@Table(name = "Strike")
public class Strike {
    
    @EmbeddedId
    private StrikeMapping id;

    @Column(name = "is_summarized")
    @JsonProperty("is_summarized")
    private boolean isSummarized;
    @Column(name = "is_quized")
    @JsonProperty("is_quized")
    private boolean isQuized;

    public Strike() {
    }

    public Strike(Long userId, LocalDate visitDate, Long newsId, boolean isSummarized, boolean isQuized) {
        this.id = new StrikeMapping(userId, newsId, visitDate);
        this.isSummarized = isSummarized;
        this.isQuized = isQuized;
    }


    // Optional: 편의를 위한 transient getter
    @Transient
    public Long getUserId() {
        return id.getUserId();
    }

    @Transient
    public Long getNewsId() {
        return id.getNewsId();
    }

    @Transient
    public LocalDate getVisitDate() {
        return id.getVisitDate();
    }
}
