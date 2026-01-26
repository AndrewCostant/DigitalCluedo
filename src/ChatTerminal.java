import domain.*;
import java.util.*;

public class ChatTerminal {
    public static void main(String[] args) {
        NormalCell cell1 = new NormalCell(5,2);
        Set<Cell> destinations = Board.getInstance().possibleDestinations(cell1, 2);
        System.out.println("Number of destinations: " + destinations.size());
        for (Cell cell : destinations) {
            System.out.println("Possible destination: (" + cell + ")");
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome giocatore 1: ");
        String name1 = scanner.nextLine();

        System.out.print("Nome giocatore 2: ");
        String name2 = scanner.nextLine();


        RoomCell startPosition = (RoomCell) Board.getInstance().getCellXY(3, 3);
        Player player1 = new Player(name1, startPosition);
        Player player2 = new Player(name2, startPosition);
        
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        CluedoGame.getInstance().setPlayers(players);

        CluedoGame.getInstance().setCurrentPlayer();
        Player currentPlayer = CluedoGame.getInstance().getCurrentPlayer();
        System.out.print("Inizia il giocatore: " + currentPlayer.getUsername() + "\n");
        System.out.print("Premi invio per lanciare i dadi... \n");
        scanner.nextLine();
        Set<Cell> possibleDestinations = CluedoGame.getInstance().rollDices();
        System.out.print("Hai " + possibleDestinations.size() + " possibili destinazioni: \n");
        int number = 1;
        for (Cell destination: possibleDestinations) {
            System.out.println(number + ": " + destination);
            number++;
        }
        scanner.close();
    }
}
