import java.util.ArrayList;
import java.util.Set;

public class CluedoGame {


	private static volatile CluedoGame instance;

	private int numberOfPlayer;
	private Player currentPlayer;
	private ArrayList<Player> players;
	private Dice dice;
	//winning solution
	private SuspectC suspectW;
	private RoomC roomW;
	private WeaponC weaponW;

	private CluedoGame() {
		this.dice = new Dice(6);
		this.players = new ArrayList<Player>();
		this.numberOfPlayer = this.players.size();
		this.currentPlayer = this.players.get(0);
		// TODO - metodo per inizializzare la soluzione vincente e distribuire le carte ai giocatori
		suspectW = new SuspectC();
		roomW = new RoomC();
		weaponW = new WeaponC();
	}

	public static CluedoGame getInstance() {
		if (instance == null) {
			instance = new CluedoGame();
		}
		return instance;
	}
	

	/**
	 * Rolls the dice and returns all the possible moves.
	 */
	public Set<Cell> rollDices() {
		int rollResult = dice.roll() + dice.roll();
		Cell currentPosition = currentPlayer.getPosition();
		Set<Cell> possibleMoves = Board.getInstance().possibleDestinations(currentPosition, rollResult);
		return possibleMoves;
	}

	/**
	 * Moves the current player to a new cell.	
	 * @param newPosition
	 */
	public String goToCell(Cell newPosition) {
		return currentPlayer.moveTo(newPosition);
	}

	public void endTurn() {
		// TODO - implement CluedoGame.endTurn
		throw new UnsupportedOperationException();
	}

	public void endGame() {
		// TODO - implement CluedoGame.endGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Verifies a player's guess.
	 * @param newGuess
	 */
	public String verifyGuess(Guess newGuess) {
		if(  newGuess.getSuspect().getName().equals(suspectW.getName()) &&
				newGuess.getRoom().getName().equals(roomW.getName()) &&
				newGuess.getWeapon().getName().equals(weaponW.getName()) ) {
			CluedoGame.getInstance().endGame();
			return "Correct Guess! You win!";
		} else {
			int i = players.indexOf(currentPlayer);
			boolean t = true;
			while ( t ) {
				i++;
				if (i == numberOfPlayer) {
					i = 0;
				}
				Player player = players.get(i);
				if (player != currentPlayer) {
					Card shownCard = player.showACard(newGuess);
					if (shownCard != null) {
						currentPlayer.addKnownCard(shownCard);
						t = false;
					}
				}else{
					t = false;
				}
			}
			CluedoGame.getInstance().endTurn();
			return "Wrong Guess! Game continues.";
		}
	}

	// GETTERS AND SETTERS
	public int getNumberOfPlayer() {
		return this.numberOfPlayer;
	}	

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}