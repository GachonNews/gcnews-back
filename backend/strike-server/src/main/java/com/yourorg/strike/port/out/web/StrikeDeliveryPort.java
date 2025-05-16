package com.yourorg.strike.port.out.web;

import com.yourorg.strike.domain.entity.Strike; // Import Strike entity

public interface StrikeDeliveryPort {
    void deliverStrikeInfo(Strike strike); // Method to deliver strike information
}
