package com.cluedo.domain;

import java.util.ArrayList;

import com.cluedo.domain.dto.*;

public class PeekCard implements EffectStrategy {

	@Override
	public DoActionResult effect(Player player) {
		ArrayList<Card> cardsShown = new ArrayList<>();
		Player nextPlayer = CluedoGame.getInstance().getNextPlayer();
		Card peekedCard = nextPlayer.peekRandomCard();
		cardsShown.add(peekedCard);
		player.addKnownCard(peekedCard, nextPlayer.getUsername());
		return new ChanceDoAction(player.getPosition(), false, cardsShown, this);
	}

	@Override
	public String getDescription() {
		return "You have peeked a random card from the next player's hand!";
	}

}