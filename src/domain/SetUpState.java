package domain;

import java.util.*;

public class SetUpState extends AbstractGameState{

	@Override
    public void setPlayers(ArrayList<String> names) {
        ArrayList<Player> players = new ArrayList<Player>();
        for (String name : names) {
            Player player = new Player(name, Board.getInstance().getStartPosition());
            players.add(player);
        }
        CluedoGame.getInstance().addPlayers(players);
    }

    @Override
    public void setUpGame() {
        CluedoGame c = CluedoGame.getInstance();
        c.createGameDeck();
		int cardsPerPlayer = c.getGameDeck().size() / c.getNumberOfPlayers();
		for ( Player player : c.getPlayers() ) {
			for ( int i = 0; i < cardsPerPlayer; i++ ) {
				player.addCardToHand( c.getGameDeck().remove(0) );
			}
		}
		while(!c.getGameDeck().isEmpty()) {
			for ( Player player : c.getPlayers() ) {
				player.addKnownCard(c.getGameDeck().get(0), "Everyone");
			}
			c.getGameDeck().remove(0);
		}
		c.setCurrentPlayer(); 
		c.setState(new RollState());
    }  
    
}
