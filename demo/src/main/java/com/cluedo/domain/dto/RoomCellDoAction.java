package com.cluedo.domain.dto;

import java.util.ArrayList;
import com.cluedo.domain.*;

public record RoomCellDoAction(Cell cell, Boolean gameEnded, ArrayList<Card> cardsShown) implements DoActionResult {
    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public Boolean isGameEnded() {
        return gameEnded;
    }

    @Override
    public ArrayList<Card> getCardsShown() {
        return cardsShown;
    }

    @Override
    public EffectStrategy getEffect() {
        return null;
    }
    
}
