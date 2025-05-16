package com.yourorg.strike.port.out.persistence;


import com.yourorg.strike.domain.entity.Strike;

public interface StrikeWritePort {
    Strike saveStrike(Strike strike);
}