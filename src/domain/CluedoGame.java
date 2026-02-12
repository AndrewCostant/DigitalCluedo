package domain;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import domain.dto.*;


public class CluedoGame {


	private static volatile CluedoGame instance;

	private int numberOfPlayers;
	private Player currentPlayer;
	private int currentPlayerIndex;
	private ArrayList<Player> players;
	private Dice dice;
	//game deck
	private ArrayList<Card> gameDeck;
	//winning solution
	private Triplet winningTriplet;
	private GameState state;

	private CluedoGame() {
		this.dice = new Dice(3);
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
	
	public void startGame(){
		this.state.setUpGame();
	}

	public void createGameDeck() {
		ArrayList<Card> deck = createSpecificDecks("suspect");
		SuspectC suspectW = (SuspectC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks("room");
		RoomC roomW = (RoomC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks("weapon");
		WeaponC weaponW = (WeaponC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		this.winningTriplet = new Triplet(suspectW, weaponW, roomW);
		Collections.shuffle(gameDeck);
	}

	

	public ArrayList<Card> createSpecificDecks(String className) {
		String filePath = "utility/" + className.toLowerCase() + "Card.txt";
		ArrayList<Card> deck = new ArrayList<>();

		InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

		if (is == null) {
			throw new RuntimeException("File not found in classpath: " + filePath);
		}

		try (Scanner sc = new Scanner(is)) {
			while (sc.hasNextLine()) {
				String name = sc.nextLine();
				switch (className) {
					case "suspect" -> deck.add(new SuspectC(name));
					case "room" -> deck.add(new RoomC(name));
					case "weapon" -> deck.add(new WeaponC(name));
				}
			}
		}

		return deck;
	}

	public String specificDeckByTypeToString(String className) throws Exception {
		String path = "utility/" + className.toLowerCase() + "Card.txt";
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

	/**
	 * Rolls the dice and returns all the possible moves.
	 */
	public RollResult rollDices() {
		return this.state.rollDice();
	}

	public int gamble(){
		return dice.roll() + dice.roll();
	}

	public Card getWinningCard(){
		return winningTriplet.suspectPerson();
	}

	public void addKnownCardPlayers(Card card){
		for (Player p: players){
			if (p.equals(currentPlayer)){
				continue;
			} else {
				p.addKnownCard(card, currentPlayer.getUsername());
			}
		}
	}

	/**
	 * Moves the current player to a new cell.	
	 * @param newPosition
	 */
	public ActionResult goToCell(Cell newPosition) {
		return this.state.moveTo(newPosition);
	}

	public void endTurn() {
		setCurrentPlayer();
	}

	//***************************************************************************************************************************************** */
	public String endGame() {
		return "Correct guess! You win!";
	}

	/**
	 * Verifies a player's guess. if the guess is correct, ends the game. 
	 * Otherwise, asks other players to show a card then, if one of them shows a card, adds it to the current player's known cards otherwise, some of these cards are in the winning solution.
	 * Then ends the current player's turn.
	 * @param newGuess
	 */
	public DoActionResult verifyGuess(Triplet newGuess) {
		return this.state.makeAssumption(newGuess);
	}

	// GETTERS AND SETTERS
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

	public ArrayList<Card> getGameDeck() {
		return this.gameDeck;
	}



	/* public ArrayList<Card> createSpecificDecks(String className) {
		ArrayList<Card> deck = new ArrayList<Card>();
		switch (className) {
			case "SuspectC":
				try {
					Scanner sc = new Scanner(new File("/utility/suspectCard.txt"));
					while (sc.hasNextLine()) {
						String name = sc.nextLine();
						SuspectC suspect = new SuspectC(name);
						deck.add(suspect);
					}
					sc.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case "RoomC":
				try {
					Scanner sc = new Scanner(new File("/utility/roomCard.txt"));
					while (sc.hasNextLine()) {
						String name = sc.nextLine();
						RoomC room = new RoomC(name);
						deck.add(room);
					}
					sc.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case "WeaponC":
				try {
					Scanner sc = new Scanner(new File("/utility/weaponCard.txt"));
					while (sc.hasNextLine()) {
						String name = sc.nextLine();
						WeaponC weapon = new WeaponC(name);
						deck.add(weapon);
					}
					sc.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
		}
		return deck;
	} */
}