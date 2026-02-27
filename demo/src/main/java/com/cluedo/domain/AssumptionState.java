package com.cluedo.domain;

import java.util.ArrayList;

import com.cluedo.config.GameEvent;
import com.cluedo.domain.dto.*;

public class AssumptionState extends AbstractGameState{
    @Override
    public DoActionResult makeAssumption(Triplet guess) {
        CluedoGame c = CluedoGame.getInstance();

        Player currentPlayer = c.getCurrentPlayer();
        int numberOfPlayers = c.getNumberOfPlayers();

        Cell cell = currentPlayer.getPosition();
		ArrayList<Card> result = new ArrayList<Card>();

        
		if(  c.getWinningTriplet().equals(guess) ) {
            c.setState(new EndGameState());
			return new RoomCellDoAction(cell, true, null);
		} else {
			int i = c.getPlayers().indexOf(currentPlayer);
			boolean t = true;
			while ( t ) {
				i++;
				if (i == numberOfPlayers) {
					i = 0;
				}
				Player player = c.getPlayers().get(i);
				//se nessuno ha mostrato una carta esce dal loop e bisogna aggiungere le carte dell'assunzione che il player corrente non ha nel mazzo known card del player stesso con il valore "scoperto"
				if (player != currentPlayer) {
					Card shownCard = player.showACard(guess);
					// mostra username del player che mostra la carta e la carta mostrata
					if (shownCard != null) {
						currentPlayer.addKnownCard(shownCard, player.getUsername());
						result.add(shownCard);
						t = false;
					}
				}
				else{
					break;
				}
			}
			if (t) {
				result.addAll(currentPlayer.addSuspectCards(guess));
			}
			return new RoomCellDoAction(cell, false, result);
		}
    }

    @Override
    public void endTurn() {
        CluedoGame c = CluedoGame.getInstance();
        c.setCurrentPlayer();
		c.resetCurrentTurn();
        c.setState(new RollState());
		c.notifyObservers(GameEvent.ROLL_DICES);
    }
}
