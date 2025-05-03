package com.yourorg.summary.port.in.message;

import com.yourorg.summary.domain.entity.Summary;

public interface SummaryJobPort {
    void requestSummaryJob(Summary summary);
}
