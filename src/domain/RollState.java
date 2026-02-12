package domain;

import java.util.Set;

import domain.dto.RollResult;

public class RollState extends AbstractGameState {
    @Override
    public RollResult rollDice() {
        CluedoGame c = CluedoGame.getInstance();
        int rollResult = c.gamble();
		Cell currentPosition = c.getCurrentPlayer().getPosition();
		Set<Cell> possibleMoves = Board.getInstance().possibleDestinations(currentPosition, rollResult);

        c.setState(new MoveState());
        
		return new RollResult(rollResult, possibleMoves);
    }
}
