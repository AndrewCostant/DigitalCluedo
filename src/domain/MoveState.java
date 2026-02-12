package domain;

import domain.dto.ActionResult;

public class MoveState extends AbstractGameState {
    @Override
    public ActionResult moveTo(Cell destination) {
        return CluedoGame.getInstance().getCurrentPlayer().moveTo(destination);
    }
}
