import ui.*;
import domain.CluedoGame;
import controller.GameController;

public class App {

    public static void main(String[] args) throws Exception {
        GameController gameController = new GameController();
        gameController.Game(CluedoGame.getInstance(), TerminalUI.getInstance());
        

        /*
         * NormalCell cell1 = new NormalCell(5,2);
         * Set<Cell> destinations = Board.getInstance().possibleDestinations(cell1, 2);
         * System.out.println("Number of destinations: " + destinations.size());
         * for (Cell cell : destinations) {
         * System.out.println("Possible destination: (" + cell + ")");
         * }
         */
    } 
}
