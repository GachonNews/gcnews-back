package com.yourorg.article.port.in.web;

import com.yourorg.article.adapter.in.web.dto.RecapResponse;

public interface RecapApiPort {
    RecapResponse getMonthlyRecap(Long userId, String yearMonth);
}
