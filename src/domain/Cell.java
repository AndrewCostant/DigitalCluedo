package domain;
public interface Cell {

	public abstract int getX();
	public abstract int getY();
	/**
	 * Performs the action associated with this cell.
	 */
	public abstract String action();

	/**
	 * Does an action with a suspect and a weapon for a player.
	 * @param suspect
	 * @param weapon
	 * @param player
	 */
	public String doAction(SuspectC suspect, WeaponC weapon, Player player);

}