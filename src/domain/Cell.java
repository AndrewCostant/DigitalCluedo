package domain;

import domain.dto.*;
public interface Cell {

	public abstract int getX();
	public abstract int getY();
	/**
	 * Performs the action associated with this cell.
	 */
	public abstract ActionResult action();

	/**
	 * Does an action with a suspect and a weapon for a player.
	 * @param suspect
	 * @param weapon
	 * @param player
	 */
	public DoActionResult doAction(SuspectC suspect, WeaponC weapon, Player player);

	@Override
	public abstract String toString();
	@Override
	public abstract int hashCode();
	@Override
	public abstract boolean equals(Object obj);

}