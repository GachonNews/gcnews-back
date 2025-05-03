package com.yourorg.summary.port.out.persistence;
import com.yourorg.summary.domain.entity.Summary;
import java.util.Optional;

public interface SummaryReadPort {
    Optional<Summary> SummaryRequest(Long newsId); // "load" → "find"로 변경
}