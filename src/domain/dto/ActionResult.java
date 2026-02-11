package domain.dto;

import domain.*;

public sealed interface ActionResult {
    String getType();
    Cell getCell();
    Card getCard();
    int getValue();
}

record ChanceAction(String type, Cell cell, Card chanceCard) implements ActionResult{
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

record NormalCellAction(String type, Cell cell) implements ActionResult{
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
        return null;
    }

    @Override
    public int getValue() {
        return 0;
    }
}

record NormalRoomAction(String type, Cell cell) implements ActionResult{
    @Override
    public String getType() { return type; }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public Card getCard() {
        return null;
    }

    @Override
    public int getValue() {
        return 0;
    }
}

record SecretPassageAction(String type, Cell cell) implements ActionResult{
    @Override
    public String getType() { return type; }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public Card getCard() {
        return null;
    }

    @Override
    public int getValue() {
        return 0;
    }
}

record GamblingRoomAction(String type, Cell cell, int value, Card card) implements ActionResult{
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


