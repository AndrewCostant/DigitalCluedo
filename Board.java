import java.util.ArrayList;

public class Board {

	private static volatile Board instance;
	private ArrayList<Cell> boardCells;
	private ArrayList<ChanceC> chanceDeck;
	private int dimension;

	private Board() {
		this.boardCells = initializeBoardCells();
		this.chanceDeck = initializeChanceDeck();
		this.dimension = this.boardCells.size();
	}

	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}

	private ArrayList<Cell> initializeBoardCells() {
		// TODO - implement Board.initializeBoardCells
		return this.boardCells;
	}

	private ArrayList<ChanceC> initializeChanceDeck() {
		// TODO - implement Board.initializeChanceDeck
		return this.chanceDeck;
	}

	/**
	 * Returns all possible destination cells from a starting position given a number of steps.
	 * @param startPosition
	 * @param steps
	 */
	public ArrayList<Cell> possibleDestinations(Cell startPosition, int steps) {
		// TODO - implement Board.possibleDestinations
		throw new UnsupportedOperationException();
	}

	/**
	 * Performs an action with a suspect and a weapon.
	 * @param sus
	 * @param weapon
	 */
	public String doAction(SuspectC sus, WeaponC weapon) {
		Player player = CluedoGame.getInstance().getCurrentPlayer();
		Cell playerPos = player.getPosition();
		return playerPos.doAction(sus, weapon, player);
	}

	/**
	 * Draws a chance card for a player.
	 * @param player
	 */
	public String DrawChanceC(Player player) {
		// TODO - implement Board.DrawChanceC
		throw new UnsupportedOperationException();
	}

	// GETTERS AND SETTERS
	public int getDimension() {
		return this.dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

}