package domain;

public class MakeAGuess implements EffectStrategy {

	/**
	 * 
	 * @param player
	 */
	public void effect(Player player) {
		// far vedere tutte le stanze
		Cell selectedRoom = null;
		// scegliere una stanza
		
		// spostare il player in quella stanza
		CluedoGame.getInstance().goToCell(selectedRoom); 
		Board.getInstance().doAction(null, null);
	}

}