package com.cluedo.domain;

import com.cluedo.domain.dto.DoActionResult;

public interface EffectStrategy {

	/**
	 * implement the effect of the card
	 * @param player
	 */
	public DoActionResult effect(Player player);

	public String getDescription();
}