package com.yourorg.strike.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;


@Getter
@Setter
@Embeddable
public class StrikeMapping implements Serializable {

    private Long userId;
    private Long newsId;
    @Column(name = "visit_date")
    private LocalDate visitDate;

    public StrikeMapping(){}

    public StrikeMapping(Long userId, Long newsId, LocalDate visitDate){
        this.userId=userId;
        this.newsId=newsId;
        this.visitDate=visitDate;
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof StrikeMapping)) return false;
        StrikeMapping that = (StrikeMapping) o;
        return Objects.equals(userId, that.userId)&&
                Objects.equals(newsId, that.newsId)&&
                Objects.equals(visitDate, that.visitDate);
    }

    @Override
    public int hashCode(){
        return Objects.hash(userId, newsId, visitDate);
    }
}
