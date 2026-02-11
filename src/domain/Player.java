package domain;
import java.util.*;

public class Player {

	private String username;
	private String pawnSkin;
	private Cell position;
	private ArrayList<Guess> guesses;
	private Map<String, String> knownCards;
	private ArrayList<Card> handCards;
	private ChanceC chanceCard;


	public Player(String username, String pawnSkin, Cell position) {
		this.username = username;
		this.pawnSkin = pawnSkin;
		this.position = position;
		this.guesses = new ArrayList<Guess>();
		this.knownCards = new HashMap<String, String>();
		this.handCards = new ArrayList<Card>();
		this.chanceCard = null;
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
		for (Card card: handCards) {
			if (guess.isInHand(card)) {
				return card;
			}
		}
		return null;
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

	/**
	 * Returns the list of cards in the player's hand.
	 */
	public ArrayList<Card> getHandCards() {
		return handCards;
	}

	public ChanceC getChanceCard() {
		return chanceCard;
	}

	public void setChanceCard(ChanceC chanceCard) {
		this.chanceCard = chanceCard;
	}

	// Known Cards Management

	/**	
	 * Adds a known card to the player's list of known cards.
	 * @param kCard
	 */
	public Boolean addKnownCard(Card card , String player) {
		knownCards.put(card.getName(), player);
		return true;
	}

	/**
	 * verifica se nella guess del giocare ci sono carte di cui è in possesso
	 * quelle di cui non è in possesso le aggiunge alle known card
	 */
	public Boolean addSuspectCards(Guess guess) {
		SuspectC suspect = guess.getSuspect();
		WeaponC weapon = guess.getWeapon();
		RoomC room = guess.getRoom();
		ArrayList<Card> controlList = new ArrayList<Card>();
		controlList.add(suspect);
		controlList.add(weapon);
		controlList.add(room);
		for (Card card: controlList){
			if (isInHand(card)){
				continue;
			}
			else{
				addKnownCard(card, "Sospetto");
			}
		}
		return true;
	}

	/**
	 * Searches for a known card by name.
	 * @param cardName
	 */
	public String searchKnownCard(String cardName) {
		return knownCards.get(cardName);
	}

	/**
	 * Returns the map of known cards.
	 */
	public Map<String, String> getKnownCards() {
		return knownCards;
	}

	/**
	 * Prints all known cards.
	 */
	public String printKnownCards() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : knownCards.entrySet()) {
			sb.append(entry.getKey())
			  .append(" owned by ").append(entry.getValue())
			  .append("\n");
		}
		return sb.toString();
	}

	/**
	 * Prints all hand cards.
	 */
	public String printHandCards() {
		StringBuilder sb = new StringBuilder();
		for (Card card : handCards) {
			sb.append(card.getName()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Clears all known cards.
	 */
	public void clearKnownCards() {
		knownCards.clear();
	}

	/**
	 * Peeks at a random card from the player's hand without revealing it.
	 */
	public String peekRandomCard() {
		if (handCards.isEmpty()) {
			return null; // No cards to peek at
		}
		Random rand = new Random();
		int randomIndex = rand.nextInt(handCards.size());
		Card randomCard = handCards.get(randomIndex);
		return randomCard.getName();
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

	public boolean isInHand(Card card) {
		for (Card cardH: handCards){
			if (cardH.equals(card)){
				return true;
			}
		}
        return false;
    }

	@Override
	public String toString() {
		return "Player [username=" + username + ", position=" + position + "]";
	}

}