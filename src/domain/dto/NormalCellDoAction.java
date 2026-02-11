package domain.dto;

import java.util.ArrayList;

import domain.*;

public record NormalCellDoAction(Cell cell) implements DoActionResult {
    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public Boolean isGameEnded() {
        return false;
    }

    @Override
    public ArrayList<Card> getCardsShown() {
        return null;
    }

    @Override
    public EffectStrategy getEffect() {
        return null;
    }
    
}
