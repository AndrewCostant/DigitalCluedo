package domain;

public class PeekCard implements EffectStrategy {

	/**
	 * 
	 * @param player
	 */
	public String effect(Player player) {
		Player nextPlayer = CluedoGame.getInstance().getNextPlayer();
		String peekedCard = nextPlayer.peekRandomCard();
		player.addKnownCard(peekedCard, nextPlayer.getUsername());
		return "Player" + nextPlayer.getUsername() + " has shown you " + peekedCard;
	}

}