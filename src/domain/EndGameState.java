package domain;

public class EndGameState extends AbstractGameState {
    @Override
    public void endTurn() {
        CluedoGame.getInstance().setState(new SetUpState());
        
    }
}
