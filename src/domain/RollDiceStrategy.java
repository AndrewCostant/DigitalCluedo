package domain;

import domain.dto.RollResult;

public interface RollDiceStrategy {
    RollResult possibleDestinations(Dice dice, Cell currentPosition);
    
}
