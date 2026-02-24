package com.cluedo.domain;

import java.util.*;

import com.cluedo.domain.dto.RollResult;

public class SpeedRollDiceStrategy implements RollDiceStrategy {
    @Override
    public RollResult possibleDestinations(Dice dice, Cell currentPosition) {
        int diceRoll = dice.roll(); 
        Set<Cell> ris = new  HashSet<>();
        ris.add(Board.getInstance().getCellByIndex(diceRoll - 1));
        RollResult rollResult = new RollResult(diceRoll, ris); 
        return rollResult;
    }

}
