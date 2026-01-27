package domain;
import java.util.*;

public class Player {

	private String username;
	private String pawnSkin;
	private Cell position;
	private ArrayList<Guess> guesses;
	private Map<String, String> knownCards;
	private ArrayList<Card> handCards;


	public Player(String username, String pawnSkin, Cell position) {
		this.username = username;
		this.pawnSkin = pawnSkin;
		this.position = position;
		this.guesses = new ArrayList<Guess>();
		this.knownCards = new HashMap<String, String>();
		this.handCards = new ArrayList<Card>();
	}

	public Player(String username, Cell position) {
		this.username = username;
		this.position = position;
		this.guesses = new ArrayList<Guess>();
		this.knownCards = new HashMap<String, String>();
		this.handCards = new ArrayList<Card>();
	}

	/**
	 * Updates the player's position.
	 * @param newPosition
	 */
	public String moveTo(Cell newPosition) {
		setPosition(newPosition);
		return newPosition.action();
	}


	/**
	 *  
	 * @param sus
	 * @param weapon
	 * @param room
	 */
	public String makeAGuess(SuspectC sus, WeaponC weapon, RoomC room) {
		Guess newGuess = new Guess(sus, weapon, room);
		this.guesses.add(newGuess);
		return CluedoGame.getInstance().verifyGuess(newGuess);
	}

	/**
	 * 
	 * @param guess
	 */
	public Card showACard(Guess guess) {
		// TODO - implement Player.showACard
		throw new UnsupportedOperationException();
	}

	
	// Hand Cards Management

	/**
	 * Adds a card to the player's hand.
	 * @param card
	 */
	public Boolean addCardToHand(Card card) {
		handCards.add(card);
		return true;
	}

	/**
	 * Searches for a card in the player's hand by name.
	 * @param cardName
	 */
	public Card searchCardInHand(String cardName) {
		for (Card card : handCards) {
			if (card.getName().equals(cardName)) {
				return card;
			}
		}
		return null; // Card not found
	}

	// Known Cards Management

	/**	
	 * Adds a known card to the player's list of known cards.
	 * @param kCard
	 */
	public Boolean addKnownCard(String cardName , String playerName) {
		knownCards.put(cardName, playerName);
		return true;
	}

	/**
	 * Searches for a known card by name.
	 * @param cardName
	 */
	public String searchKnownCard(String cardName) {
		return knownCards.get(cardName);
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

	public Cell getPosition() {
		return position;
	}

	public void setPosition(Cell position) {
		this.position = position;
	}


	@Override
	public String toString() {
		return "Player [username=" + username + ", position=" + position + "]";
	}

}