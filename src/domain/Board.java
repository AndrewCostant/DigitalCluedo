package domain;

import org.jgrapht.*;
import org.jgrapht.graph.*;

import java.util.*;

public class Board {

	private static volatile Board instance;
	private final Graph<Cell, DefaultEdge> graph; // Graph to represent the board layout
	private ArrayList<ArrayList<Cell>> boardCells; // Matrix of cells representing the board
	private ArrayList<ChanceC> chanceDeck;
	private int dimension; // Default dimension, can be modified

	private Board() {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.boardCells = new ArrayList<>();
		initializeBoardCells();
		this.chanceDeck = initializeChanceDeck();
		this.dimension = this.boardCells.size();
	}

	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
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
	public Set<Cell> possibleDestinations(Cell startPosition, int steps) {
		Set<Cell> visited = new HashSet<>();
        Queue<Cell> queue = new ArrayDeque<>();
        Map<Cell, Integer> depth = new HashMap<>();

        visited.add(startPosition);
        queue.add(startPosition);
        depth.put(startPosition, 0);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int d = depth.get(current);

            if (d == steps) continue;

            for (DefaultEdge edge : graph.edgesOf(current)) {
                Cell neighbor =
                        Graphs.getOppositeVertex(graph, edge, current);

                if (visited.add(neighbor)) {
                    depth.put(neighbor, d + 1);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
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

	//Method to manage the graph
	private void addDoor(Cell a, Cell b) {
        graph.addEdge(a, b);
    }

	

	private void initializeBoardCells() {
		Room library = new NormalRoom(0, 0, "Library");
		ChanceCell chanceCell0 = new ChanceCell(0, 1);
		NormalCell cell1 = new NormalCell(0, 2);
		Room homeGym = new NormalRoom(0, 3, "Home Gym");
		NormalCell cell2 = new NormalCell(0, 4);
		Room Lounge = new NormalRoom(0,5, "Lounge");
		Room Bathroom = new NormalRoom(0,6, "Bathroom");
		Room Kitchen = new NormalRoom(1,0, "Kitchen");
		NormalCell cell3 = new NormalCell(1, 1);
		NormalCell cell4 = new NormalCell(1, 2);
		NormalCell cell5 = new NormalCell(1, 3);
		NormalCell cell6 = new NormalCell(1, 4);
		ChanceCell chanceCell1 = new ChanceCell(1,5);
		NormalCell cell7 = new NormalCell(1, 6);
		NormalCell cell8 = new NormalCell(2, 0);
		NormalCell cell9 = new NormalCell(2, 1);
		ChanceCell chanceCell2 = new ChanceCell(2, 2);
		NormalCell cell10 = new NormalCell(2, 3);
		NormalCell cell11 = new NormalCell(2, 4);
		NormalCell cell12 = new NormalCell(2, 5);
		Room Garden = new NormalRoom(2,6, "Garden");
		Room DiningRoom = new NormalRoom(3,0, "Dining Room");
		NormalCell cell13 = new NormalCell(3, 1);
		NormalCell cell14 = new NormalCell(3, 2);
		Room Hall = new NormalRoom(3,3, "Hall");
		NormalCell cell15 = new NormalCell(3, 4);
		NormalCell cell16 = new NormalCell(3, 5);
		ChanceCell chanceCell3 = new ChanceCell(3,6);
		NormalCell cell17 = new NormalCell(4, 0);
		ChanceCell chanceCell4 = new ChanceCell(4, 1);
		NormalCell cell18 = new NormalCell(4, 2);
		NormalCell cell19 = new NormalCell(4, 3);
		NormalCell cell20 = new NormalCell(4, 4);
		NormalCell cell21 = new NormalCell(4, 5);
		NormalCell cell22 = new NormalCell(4, 6);
		Room BedRoom = new Room(5,0, "Bed Room");
		Room Study = new Room(5,1, "Study");
		NormalCell cell23 = new NormalCell(5, 2);
		ChanceCell chanceCell5 = new ChanceCell(5,3);
		NormalCell cell24 = new NormalCell(5, 4);
		ChanceCell chanceCell6 = new ChanceCell(5,5);
		Room Greenhouse = new Room(5,6, "Greenhouse");
		ChanceCell chanceCell7 = new ChanceCell(6,0);
		NormalCell cell25 = new NormalCell(6, 1);
		NormalCell cell26 = new NormalCell(6, 2);
		Room Balcony = new Room(6,3, "Balcony");
		NormalCell cell27 = new NormalCell(6, 4);
		NormalCell cell28 = new NormalCell(6, 5);
		NormalCell cell29 = new NormalCell(6,6);
		// Adding vertices to the graph
		graph.addVertex(library);
		graph.addVertex(chanceCell0);
		graph.addVertex(cell1);
		graph.addVertex(homeGym);
		graph.addVertex(cell2);
		graph.addVertex(Lounge);
		graph.addVertex(Bathroom);
		graph.addVertex(Kitchen);
		graph.addVertex(cell3);
		graph.addVertex(cell4);
		graph.addVertex(cell5);
		graph.addVertex(cell6);
		graph.addVertex(chanceCell1);
		graph.addVertex(cell7);
		graph.addVertex(cell8);
		graph.addVertex(cell9);
		graph.addVertex(chanceCell2);
		graph.addVertex(cell11);
		graph.addVertex(cell12);
		graph.addVertex(cell13);
		graph.addVertex(Garden);
		graph.addVertex(DiningRoom);
		graph.addVertex(cell14);
		graph.addVertex(cell15);
		graph.addVertex(Hall);
		graph.addVertex(cell16);
		graph.addVertex(cell17);
		graph.addVertex(chanceCell3);
		graph.addVertex(cell18);
		graph.addVertex(chanceCell4);
		graph.addVertex(cell19);
		graph.addVertex(cell20);
		graph.addVertex(cell21);
		graph.addVertex(cell22);
		graph.addVertex(cell23);
		graph.addVertex(BedRoom);
		graph.addVertex(Study);
		graph.addVertex(cell24);
		graph.addVertex(chanceCell5);
		graph.addVertex(cell25);
		graph.addVertex(chanceCell6);
		graph.addVertex(Greenhouse);
		graph.addVertex(chanceCell7);
		graph.addVertex(cell26);
		graph.addVertex(cell27);
		graph.addVertex(Balcony);
		graph.addVertex(cell28);
		graph.addVertex(cell29);
		// Adding edges to the graph (defining connections between cells)
		addDoor(library, chanceCell0);
		addDoor(chanceCell0, cell1);
		addDoor(chanceCell0, cell3);
		addDoor(cell1, homeGym);
		addDoor(cell1, cell4);
		addDoor(cell2, Lounge);
		addDoor(cell2, cell6);
		addDoor(Lounge, Bathroom);
		addDoor(Bathroom, cell7);
		// riga 1
		addDoor(Kitchen, cell8);   
		addDoor(cell3, cell4);
		addDoor(cell3, cell9);
		addDoor(cell4, chanceCell2);
		addDoor(cell4, cell5);
		addDoor(cell5, cell6);
		addDoor(cell5, cell10);
		addDoor(cell6, chanceCell1);
		addDoor(cell6, cell11);
		addDoor(chanceCell1, cell7);
		addDoor(chanceCell1, cell12);
		// riga 2
		addDoor(cell8, cell9);
		addDoor(cell9, chanceCell2);
		addDoor(chanceCell2, cell10);
		addDoor(cell10, cell11);
		addDoor(cell11, cell12);
		addDoor(cell9, cell13);
		addDoor(chanceCell2, cell14);
		addDoor(cell10, Hall);
		addDoor(cell11, cell15);
		addDoor(cell12, cell16);
		addDoor(Garden, chanceCell3);
		// riga 3
		addDoor(DiningRoom, cell13);
		addDoor(cell13, cell14);
		addDoor(cell14, Hall);
		addDoor(Hall, cell15);
		addDoor(cell15, cell16);
		addDoor(cell16, chanceCell3);
		addDoor(DiningRoom, cell17);
		addDoor(cell13, chanceCell4);
		addDoor(cell14, cell18);
		addDoor(Hall, cell19);
		addDoor(cell15, cell20);
		addDoor(cell16, cell21);
		addDoor(chanceCell3, cell22);
		// riga 4
		addDoor(cell17, chanceCell4);
		addDoor(chanceCell4, cell18);
		addDoor(cell18, cell19);
		addDoor(cell19, cell20);
		addDoor(cell20, cell21);
		addDoor(cell21, cell22);
		addDoor(cell18, cell23);
		addDoor(chanceCell4, Study);
		addDoor(cell19, chanceCell5);
		addDoor(cell20, cell24);
		addDoor(cell21, chanceCell6);
		// riga 5
		addDoor(BedRoom, Study);
		addDoor(Study, cell23);
		addDoor(cell23, chanceCell5);
		addDoor(chanceCell5, cell24);
		addDoor(cell24, chanceCell6);
		addDoor(chanceCell6, Greenhouse);
		addDoor(BedRoom, chanceCell7);
		addDoor(cell23, cell26);
		addDoor(chanceCell5, Balcony);
		addDoor(cell24, cell27);
		addDoor(chanceCell6, cell28);
		addDoor(Greenhouse, cell29);
		// riga 6
		addDoor(chanceCell7, cell25);
		addDoor(cell25, cell26);
		addDoor(cell27, cell28);
		addDoor(cell28, cell29);
	}

}