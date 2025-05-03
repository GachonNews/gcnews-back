package com.yourorg.summary.port.out.persistence;
import com.yourorg.summary.domain.entity.Summary;
public interface SummarySavePort {
    void saveSummary(Summary summary);
}
