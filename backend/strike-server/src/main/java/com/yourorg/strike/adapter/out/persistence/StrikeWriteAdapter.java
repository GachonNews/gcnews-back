package com.yourorg.strike.adapter.out.persistence;

import com.yourorg.strike.domain.entity.Strike;
import com.yourorg.strike.port.out.persistence.StrikeWritePort;
import com.yourorg.strike.adapter.out.repository.StrikeRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StrikeWriteAdapter implements StrikeWritePort {

    private final StrikeRepository strikeRepository;

    @Override
    public Strike saveStrike(Strike strike) {
        return strikeRepository.save(strike);
    }
    
}
