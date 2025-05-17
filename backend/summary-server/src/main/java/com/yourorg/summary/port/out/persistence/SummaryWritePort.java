package com.yourorg.summary.port.out.persistence;
import com.yourorg.summary.domain.entity.Summary;
public interface SummaryWritePort {
    void saveSummary(Summary summary);
}
