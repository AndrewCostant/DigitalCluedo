package com.cluedo.domain;

import com.cluedo.domain.dto.RollResult;

public interface RollDiceStrategy {
    RollResult possibleDestinations(Dice dice, Cell currentPosition);
    
}
