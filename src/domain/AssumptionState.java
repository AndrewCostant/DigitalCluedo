package domain;

import java.util.ArrayList;

import domain.dto.DoActionResult;
import domain.dto.RoomCellDoAction;

public class AssumptionState extends AbstractGameState{
    @Override
    public DoActionResult makeAssumption(Guess guess) {
        CluedoGame c = CluedoGame.getInstance();
        Cell cell = c.getCurrentPlayer().getPosition();
		ArrayList<Card> result = new ArrayList<Card>();
		if(  guess.getSuspect().getName().equals(c.getSuspectW().getName()) &&
				guess.getRoom().getName().equals(roomW.getName()) &&
				guess.getWeapon().getName().equals(weaponW.getName()) ) {
			return new RoomCellDoAction(cell, true, null);
		} else {
			int i = players.indexOf(currentPlayer);
			boolean t = true;
			while ( t ) {
				i++;
				if (i == numberOfPlayers) {
					i = 0;
				}
				Player player = players.get(i);
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
}
