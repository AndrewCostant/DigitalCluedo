package domain.dto;

import java.util.ArrayList;

import domain.*;

public record ChanceDoAction(Cell cell, Boolean gameEnded, ArrayList<Card> cardsShown, EffectStrategy effect) implements DoActionResult {
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
        return effect;
    }
}

