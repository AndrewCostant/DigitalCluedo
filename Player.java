public class Player {

	private String username;
	private String pawnSkin;

	public Cell getPosition() {
		// TODO - implement Player.getPosition
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newPosition
	 */
	public string moveTo(Cell newPosition) {
		// TODO - implement Player.moveTo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newGuess
	 */
	public void addGuess(Guess newGuess) {
		// TODO - implement Player.addGuess
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param sus
	 * @param weapon
	 * @param room
	 */
	public string newGuess(SuspectC sus, WeaponC weapon, RoomC room) {
		// TODO - implement Player.newGuess
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param kCard
	 */
	public Boolean addKnownCard(Card kCard) {
		// TODO - implement Player.addKnownCard
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param guess
	 */
	public Card showACard(Guess guess) {
		// TODO - implement Player.showACard
		throw new UnsupportedOperationException();
	}

	// GETTERS AND SETTERS
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPawnSkin() {
		return pawnSkin;
	}

	public void setPawnSkin(String pawnSkin) {
		this.pawnSkin = pawnSkin;
	}

}