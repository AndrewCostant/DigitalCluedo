package domain;

public class PeekCard implements EffectStrategy {

	/**
	 * 
	 * @param player
	 */
	public void effect(Player player) {
		Player nextPlayer = CluedoGame.getInstance().getNextPlayer();
		String peekedCard = nextPlayer.peekRandomCard();
		player.addKnownCard(peekedCard, nextPlayer.getUsername());
	}

}