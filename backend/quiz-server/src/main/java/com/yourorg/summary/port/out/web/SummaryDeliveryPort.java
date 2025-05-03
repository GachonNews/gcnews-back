package com.yourorg.summary.port.out.web;
import com.yourorg.summary.domain.entity.Summary;

public interface SummaryDeliveryPort {
    void deliverSummary(Summary summary);
}
