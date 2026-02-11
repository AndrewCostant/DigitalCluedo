package domain.dto;

import domain.*;

public record GamblingRoomAction(String type, Cell cell, int value, Card card) implements ActionResult{
    @Override
    public String getType() { return type; }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public Card getCard() {
        return card;
    }

    @Override
    public int getValue() {
        return value;
    }
}


