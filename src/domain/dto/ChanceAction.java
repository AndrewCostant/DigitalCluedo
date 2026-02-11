package domain.dto;

import domain.*;


public record ChanceAction(String type, Cell cell, Card chanceCard) implements ActionResult{
    @Override
    public String getType() { 
        return type; 
    }

    @Override
    public Cell getCell() { 
        return cell; 
    }

    @Override
    public Card getCard() {
        return chanceCard;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
