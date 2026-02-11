package domain.dto;

import domain.*;

public record NormalRoomAction(String type, Cell cell) implements ActionResult{
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