package com.cluedo.domain;

import org.jgrapht.*;
import org.jgrapht.graph.*;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.cluedo.config.GameConfig;
import com.cluedo.config.GameEvent;
import com.cluedo.domain.dto.*;
import com.cluedo.view.GameObserver;

public class Board implements Subject {

	private static volatile Board instance;
	private final Graph<Cell, DefaultEdge> graph; // Graph to represent the board layout
	private ArrayList<Cell> boardCells; // List of all cells on the board
	private Cell startPosition; // Starting position for players
	private ArrayList<ChanceC> chanceDeck;
	private int dimension; // Default dimension, can be modified
	private Map<GameEvent, Set<GameObserver>> observers; // Map to manage observers for different game events
	

	private Board() {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.boardCells = new ArrayList<>();
		this.observers = new HashMap<>();
	}

	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}

	/*******************************BOARD MANAGEMENT***********************************/

	public void initializeBoard(){
		loadBoardFromJson(CluedoGame.getInstance().getGameModeFactory().mapPath());
		this.chanceDeck = initializeChanceDeck();
		this.dimension = (int) Math.sqrt((double) boardCells.size());
	}

	/**
	 * Creates the boards and the graph by reading the related JSON
	 * @param path
	 */
	private void loadBoardFromJson(String path) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode root = mapper.readTree(getClass().getClassLoader().getResourceAsStream(path));

			Map<String, Cell> cells = new HashMap<>();

			// Creation of vertices (CELL)
			for (JsonNode c : root.get("cells")) {
				String type = c.get("type").asText();
				String name = c.get("name").asText();
				int x = c.get("x").asInt();
				int y = c.get("y").asInt();

				Cell cell = CellFactory.createCell(x, y, type, name);
				if( name.toUpperCase().equals(GameConfig.START_POSITION) ) {
					this.startPosition = cell;
				}
				graph.addVertex(cell);
				this.boardCells.add(cell);
				cells.put(x + "," + y, cell);
			}

			// Creation of edges (DOORS)
			for (JsonNode e : root.get("edges")) {
				JsonNode from = e.get(0);
				JsonNode to = e.get(1);

				int x1 = from.get(0).asInt();
				int y1 = from.get(1).asInt();
				int x2 = to.get(0).asInt();
				int y2 = to.get(1).asInt();

				Cell a = cells.get(x1 + "," + y1);
				Cell b = cells.get(x2 + "," + y2);

				if (a == null || b == null) {
					throw new IllegalStateException("Edge refers to missing cell: (" +
						x1 + "," + y1 + ") -> (" + x2 + "," + y2 + ")");
				}

				addDoor(a, b);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the set of edges (doors) connected to a given cell.
	 * @param cell
	 * @return
	 */
	public Set<DefaultEdge> edgesOf(Cell cell) {
		return graph.edgesOf(cell);
	}

	/**
	 * Adds a door (edge) between two cells in the graph.
	 * @param a
	 * @param b
	 */
	private void addDoor(Cell a, Cell b) {
        graph.addEdge(a, b);
    }

	/**
	 * Returns the opposite vertex of a given cell and edge in the graph.
	 * @param cell
	 * @param edge
	 * @return
	 */
	public Cell getOppositeVertex(Cell cell, DefaultEdge edge) {
		return Graphs.getOppositeVertex(graph, edge, cell);
	}

	/**
	 * Returns the cell at the specified index in the boardCells list.
	 * @param index
	 * @return
	 */
	public Cell getCellByIndex(int index) {
		if (index < 0 || index >= boardCells.size()) {
			throw new IndexOutOfBoundsException("Invalid cell index: " + index);
		}
		return boardCells.get(index);
	}

	/**
	 * Returns the cell at the given coordinates (x, y).
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return The cell at (x, y) or null if not found.
	 */
	public Cell getCellXY(int x, int y) {
		for (Cell cell : graph.vertexSet()) {
			if (cell.getX() == x && cell.getY() == y) {
				return cell;
			}
		}
		return null; // Cell not found
	}

	/*******************************CHANCE DECK***********************************/

	/**
	 * Initializes the chance card deck by reading card names from a text file and creating ChanceC objects using a CardFactory.
	 * @return An ArrayList of ChanceC objects representing the chance card deck.
	 */
	private ArrayList<ChanceC> initializeChanceDeck() {
		String filePath = CluedoGame.getInstance().getGameModeFactory().chanceCardPath();
		ArrayList<ChanceC> deck = new ArrayList<>();
		String type = filePath.toLowerCase();
		String[] parts = type.split("/");
		type = parts[parts.length - 1];
		type = type.split("_")[1];
		InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);

		if (is == null) {
			throw new RuntimeException("File not found in classpath: " + filePath);
		}

		try (Scanner sc = new Scanner(is)) {
			while (sc.hasNextLine()) {
				String name = sc.nextLine();
				ChanceC card = (ChanceC) CardFactory.createCard(type, name);
				deck.add(card);
			}
		}
		return deck;
	}
	
	/**
	 * Draws a chance card for a player.
	 */
	public ChanceC drawChanceC() {
		return chanceDeck.get(ThreadLocalRandom.current().nextInt(chanceDeck.size()));
	}
	
	/*******************************DO ACTION***********************************/

	/**
	 * Performs an action with a suspect and a weapon.
	 * @param sus The suspect to perform the action with.
	 * @param weapon The weapon to perform the action with.
	 */
	public DoActionResult doAction(SuspectC sus, WeaponC weapon) {
		Player player = CluedoGame.getInstance().getCurrentPlayer();
		Cell playerPos = player.getPosition();
		DoActionResult result = playerPos.doAction(sus, weapon, player);
		CluedoGame.getInstance().addDoActionResult(result);
		notifyObservers(GameEvent.END_TURN);
		return result;
	}

	
	/*******************************GETTERS AND SETTERS***********************************/

	public int getDimension() {
		return this.dimension;
	}

	public Cell getStartPosition() {
		return this.startPosition;
	}

	public int getNumCells() {
		return boardCells.size();
	}

	/*******************************OBSERVER PATTERN METHODS***********************************/

	@Override
	public void registerObserver(GameObserver observer, GameEvent event) {
		observers.computeIfAbsent(event, k -> new HashSet<>()).add(observer);
	}

	@Override
	public void registerObserver(GameObserver observer, Set<GameEvent> events) {
		for (GameEvent event : events) {
			registerObserver(observer, event);
		}
	}

	@Override
	public void removeObserver(GameObserver observer) {
		for (Set<GameObserver> observersForEvent : observers.values()) {
			observersForEvent.remove(observer);
		}
	}

	@Override
	public void notifyObservers(GameEvent event) {
		Set<GameObserver> observersForEvent = observers.get(event);
		if (observersForEvent != null) {
			for (GameObserver observer : observersForEvent) {
				try {
					observer.update(event);
				} catch (Exception e) {
					throw new RuntimeException("Error notifying observer", e);
				}
			}
		}
	}
}