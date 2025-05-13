// src/main/java/com/yourorg/summary/port/out/persistence/SummaryReadPort.java
package com.yourorg.summary.port.out.persistence;

import com.yourorg.summary.domain.entity.Summary;
import java.util.Optional;

public interface SummaryReadPort {
    Optional<Summary> findByCrawlingId(Long crawlingId);
}
