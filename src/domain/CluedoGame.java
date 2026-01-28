package domain;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collections;
import java.util.Scanner;

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
		this.currentPlayerIndex = 0;
		this.numberOfPlayers = 0;
	}

	public static CluedoGame getInstance() {
		if (instance == null) {
			instance = new CluedoGame();
		}
		return instance;
	}
	
	public boolean startGame( ArrayList<Player> players ){
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
				player.addKnownCard(gameDeck.get(0).getName(), "Everyone");
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
		ArrayList<Card> deck = createSpecificDecks("SuspectC");
		this.suspectW = (SuspectC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks("RoomC");
		this.roomW = (RoomC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		deck = createSpecificDecks("WeaponC");
		this.weaponW = (WeaponC) deck.remove( (int)(Math.random() * deck.size()) );
		gameDeck.addAll(deck);
		Collections.shuffle(gameDeck);
	}

	public ArrayList<Card> createSpecificDecks(String className) {
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
	}

	/**
	 * Rolls the dice and returns all the possible moves.
	 */
	public Set<Cell> rollDices() {
		int rollResult = dice.roll() + dice.roll();
		System.out.println("You rolled a " + rollResult + "!");
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
		setCurrentPlayer();
	}

	public void endGame() {
		// TODO - implement CluedoGame.endGame
		throw new UnsupportedOperationException();
	}

	/**
	 * Verifies a player's guess. if the guess is correct, ends the game. 
	 * Otherwise, asks other players to show a card then, if one of them shows a card, adds it to the current player's known cards otherwise, some of these cards are in the winning solution.
	 * Then ends the current player's turn.
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
				if (i == numberOfPlayers) {
					i = 0;
				}
				Player player = players.get(i);
				//se nessuno ha mostrato una carta esce dal loop e bisogna aggiungere le carte dell'assunzione che il player corrente non ha nel mazzo known card del player stesso con il valore "scoperto"
				if (player != currentPlayer) {
					Card shownCard = player.showACard(newGuess);
					if (shownCard != null) {
						currentPlayer.addKnownCard(shownCard.getName(), player.getUsername());
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
}