package domain;

import domain.dto.DoActionResult;

public interface EffectStrategy {

	/**
	 * 
	 * @param player
	 */
	public DoActionResult effect(Player player);

	public String getDescription();
}