package domain;

import domain.dto.ActionResult;

public class MoveState extends AbstractGameState {
    @Override
    public ActionResult moveTo(Cell destination) {
        CluedoGame.getInstance().setState(new AssumptionState());
        return CluedoGame.getInstance().getCurrentPlayer().moveTo(destination);
    }
}
