package domain;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;
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
	private SuspectC suspectW;
	private RoomC roomW;
	private WeaponC weaponW;

	private CluedoGame() {
		this.dice = new Dice(3);
		this.players = new ArrayList<Player>();
		this.gameDeck = new ArrayList<Card>();
		this.currentPlayerIndex = 0;
		this.numberOfPlayers = 0;
	}

	public static CluedoGame getInstance() {
		if (instance == null) {
			instance = new CluedoGame();
		}
		return instance;
	}
	
	public boolean startGame(ArrayList<Player> players){
		setPlayers(players);
		createGameDeck();
		int cardsPerPlayer = gameDeck.size() / numberOfPlayers;
		for ( Player player : this.players ) {
			for ( int i = 0; i < cardsPerPlayer; i++ ) {
				player.addCardToHand( gameDeck.remove(0) );
			}
		}
		while(!gameDeck.isEmpty()) {
			for ( Player player : this.players ) {
				player.addKnownCard(gameDeck.get(0), "Everyone");
			}
			gameDeck.remove(0);
		}
		// set startPosition
		for ( Player player : this.players ) {
			player.setPosition(Board.getInstance().getCellXY(3,3));
		}
		setCurrentPlayer();
		return true;
	}

	public void createGameDeck() {
		ArrayList<Card> deck = createSpecificDecks("suspect");
		this.suspectW = (SuspectC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks("room");
		this.roomW = (RoomC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks("weapon");
		this.weaponW = (WeaponC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
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

	public String printSpecificDeckByType(String className) throws Exception {
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
		int rollResult = dice.roll() + dice.roll();
		Cell currentPosition = currentPlayer.getPosition();
		Set<Cell> possibleMoves = Board.getInstance().possibleDestinations(currentPosition, rollResult);
		return new RollResult(rollResult, possibleMoves);
	}

	public int gamble(){
		return dice.roll() + dice.roll();
	}

	public Card getWinningCard(){
		return suspectW;
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
		return currentPlayer.moveTo(newPosition);
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
	/************************************************************ */
	public DoActionResult verifyGuess(Guess newGuess) {
		Cell cell = currentPlayer.getPosition();
		ArrayList<Card> result = new ArrayList<Card>();
		if(  newGuess.getSuspect().getName().equals(suspectW.getName()) &&
				newGuess.getRoom().getName().equals(roomW.getName()) &&
				newGuess.getWeapon().getName().equals(weaponW.getName()) ) {
			return new RoomCellDoAction(cell, true, null);
		} else {
			int i = players.indexOf(currentPlayer);
			boolean t = true;
			while ( t ) {
				i++;
				if (i == numberOfPlayers) {
					i = 0;
				}
				Player player = players.get(i);
				//se nessuno ha mostrato una carta esce dal loop e bisogna aggiungere le carte dell'assunzione che il player corrente non ha nel mazzo known card del player stesso con il valore "scoperto"
				if (player != currentPlayer) {
					Card shownCard = player.showACard(newGuess);
					// mostra username del player che mostra la carta e la carta mostrata
					if (shownCard != null) {
						currentPlayer.addKnownCard(shownCard, player.getUsername());
						result.add(shownCard);
						t = false;
					}
				}
				else{
					break;
				}
			}
			if (t) {
				result.addAll(currentPlayer.addSuspectCards(newGuess));
			}
			return new RoomCellDoAction(cell, false, result);
		}
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

	public void setPlayers(ArrayList<Player> players) {
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

	public String getWinningTriplet() {
		return this.suspectW.getName() + this.weaponW.getName() + this.roomW.getName();
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