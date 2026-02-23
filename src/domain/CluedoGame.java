package domain;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import domain.dto.*;


public class CluedoGame {


	private static volatile CluedoGame instance;

	private ArrayList<Player> players;
	private Player currentPlayer;
	private int currentPlayerIndex;
	private int numberOfPlayers;

	//game deck
	private ArrayList<Card> gameDeck;
	//winning solution
	private Triplet winningTriplet;
	//game state
	private GameState state;
	private AbstractGameModeFactory gameModeFactory;

	private CluedoGame() {
		this.players = new ArrayList<Player>();
		this.gameDeck = new ArrayList<Card>();
		this.currentPlayerIndex = 0;
		this.numberOfPlayers = 0;
		this.state = new SetUpState();
	}

	public static CluedoGame getInstance() {
		if (instance == null) {
			instance = new CluedoGame();
		}
		return instance;
	}
	
	/*******************************GAME STATE***********************************/

	/**
	 * Sets the game mode factory to be used for creating the game deck and rolling the dice.
	 * @param gameModeFactory
	 */
	public void setGameMode(AbstractGameModeFactory gameModeFactory) {
		this.gameModeFactory = gameModeFactory;
	}

	/**
	 * Starts the game by setting up the game state, creating the game deck and selecting the winning solution.
	 */
	public void startGame() {
		this.state.setUpGame();
	}

	/**
	 * Rolls the dice and returns all the possible moves.
	 */
	public RollResult rollDices() {
		return this.state.rollDice();
	}

	/**
	 * Moves the current player to a new cell.	
	 * @param newPosition
	 */
	public ActionResult goToCell(Cell newPosition) {
		return this.state.moveTo(newPosition);
	}

	/**
	 * Return the result of the assumption
	 * @param newGuess
	 */
	public DoActionResult verifyGuess(Triplet newGuess) {
		return this.state.makeAssumption(newGuess);
	}

	public void endTurn() {
		this.state.endTurn();
	}

	/*******************************GAME DECK***********************************/

	/**
	 * Creates the game deck by reading the card names from the files specified by the game mode factory, selecting one card of each type for the winning solution and shuffling the remaining cards.
	 */
	public void createGameDeck() {
		ArrayList<Card> deck = createSpecificDecks(gameModeFactory.suspectCardPath());
		SuspectC suspectW = (SuspectC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks(gameModeFactory.roomCardPath());
		RoomC roomW = (RoomC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks(gameModeFactory.weaponCardPath());
		WeaponC weaponW = (WeaponC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		this.winningTriplet = new Triplet(suspectW, weaponW, roomW);
		Collections.shuffle(gameDeck);
	}

	
	/**
	 * Creates a specific deck of cards by reading the card names from the specified file path and creating card objects using the CardFactory. The type of card is determined by the file name.
	 * @param filePath
	 * @return
	 */
	public ArrayList<Card> createSpecificDecks(String filePath) {
		
		ArrayList<Card> deck = new ArrayList<>();

		InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

		if (is == null) {
			throw new RuntimeException("File not found in classpath: " + filePath);
		}
		String type = filePath.toLowerCase();
		String[] parts = type.split("/");
		type = parts[parts.length - 1];
		type = type.split("_")[1];
		try (Scanner sc = new Scanner(is)) {
			while (sc.hasNextLine()) {
				String name = sc.nextLine();
				Card c = CardFactory.createCard(type, name);
				deck.add(c);
			}
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while reading the file: " + e.getMessage());
		}
		return deck;
	}

	/**
	 * Reads the card names from the specified file path and returns them as a string. 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String specificDeckByTypeToString(String path) throws Exception {
		InputStream is = getClass().getClassLoader().getResourceAsStream(path);
		if (is == null) {
			return "File not found in classpath: " + path;	
		}
		StringBuilder result = new StringBuilder();
		try (Scanner sc = new Scanner(is)) {
			while (sc.hasNextLine()) {
				String name = sc.nextLine();
				result.append(name).append("\n");	
			}
			sc.close();
		}catch(Exception e){
			return "An error occurred while reading the file: " + e.getMessage();
		}
		return result.toString();
	}

	/*******************************GAME DICE***********************************/

	public int gamble(){
		return gameModeFactory.getDice().roll() + gameModeFactory.getDice().roll();
	}

	/*******************************GAME LOGIC***********************************/

	/**
	 * Adds a card to the current player's known cards and adds it to the known cards of all other players with the name of the current player as the source of information.
	 * @param card
	 */
	public void addKnownCardPlayers(Card card){
		for (Player p: players){
			if (p.equals(currentPlayer)){
				continue;
			} else {
				p.addKnownCard(card, currentPlayer.getUsername());
			}
		}
	}

	/*******************************GETTERS AND SETTERS***********************************/ 

	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}	

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer() {
		this.currentPlayer = players.get(currentPlayerIndex);
		this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.numberOfPlayers;
	}

	public void setPlayers(ArrayList<String> players) {
		this.state.setPlayers(players);
	}

	public void addPlayers(ArrayList<Player> players) {
		this.players = players;
		this.numberOfPlayers = players.size();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getNextPlayer(){
		int nextIndex = (this.currentPlayerIndex) % this.numberOfPlayers;
		return players.get(nextIndex);
	}

	public Triplet getWinningTriplet() {
		return winningTriplet;
	}

	public Card getWinningCard(){
		return winningTriplet.suspectPerson();
	}

	public ArrayList<Card> getGameDeck() {
		return this.gameDeck;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public AbstractGameModeFactory getGameModeFactory() {
		return gameModeFactory;
	}
}