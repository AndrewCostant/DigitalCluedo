package domain;

import domain.dto.RollResult;

public class RollState extends AbstractGameState {
    @Override
    public RollResult rollDice() {
        CluedoGame c = CluedoGame.getInstance();
        AbstractGameModeFactory facto = c.getGameModeFactory();
        RollDiceStrategy strategy = c.getGameModeFactory().getRollDiceStrategy();
        RollResult rollResult = strategy.possibleDestinations(facto.getDice(), c.getCurrentPlayer().getPosition());
        c.setState(new MoveState());
        
		return rollResult;
    }
}
