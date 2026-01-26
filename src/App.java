import domain.*;
import java.util.*;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println("--------------------------Digital Cluedo Demo 1----------------------------");
        System.out.println();
        System.out.println("===========================================================================");
        System.out.println();
        System.out.println("Hi, this is the first demo of Digital Cluedo.");
        System.out.println("Creating the board...");
        System.out.println("The board layout is as follows:");
        System.out.println();
        System.out.println("    y    0         1         2         3         4         5         6   ");
        System.out.println(" x  +---------+---------+---------+---------+---------+---------+---------+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 0  | Library =    ?    |         =   H G   |         =  Lounge = Bathroom|");
        System.out.println("    |         |         |         |         |         |         |         |");  
        System.out.println("    +----║----+---------+---------+---------+---------+---------+----║----+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 1  | Kitchen |         |         |         |         |         |    ?    |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +----║----+---------+---------+---------+---------+---------+---------+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 2  |         =         |    ?    |         |         |         |  Garden |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +---------+---------+---------+----║----+---------+---------+----║----+"); 
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 3  | DiningR =         |         =  Hall   =         |         |    ?    |");
        System.out.println("    |         |         |         |  P1  P2 |         |         |         |");
        System.out.println("    +----║----+---------+---------+----║----+---------+---------+---------+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 4  |         |    ?    |         |         |         |         |         |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +---------+----║----+---------+---------+---------+---------+---------+");  
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 5  | BedRoom =  Study  =         |    ?    |         |    ?    =  Ghouse |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +----║----+---------+---------+----║----+---------+---------+----║----+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 6  |    ?    |         |         | Balcony |         |         |         |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +---------+---------+---------+---------+---------+---------+---------+");    
        System.out.println();
        System.out.println("Legend: '=' and '‖' doors, '?' chances.");
        System.out.println();
        System.out.println("===========================================================================");
        System.out.println();
        System.out.println("This demo can be played by two players, called P1 and P2.");
        System.out.println("At the beginning, they are in the Hall.");
        System.out.println("Each player will take turns to roll the dice and move on the board.");
        System.out.println("Enjoy the demo!");

        Scanner scanner = new Scanner(System.in);
        System.out.print("P1's name: ");
        String name1 = scanner.nextLine();

        System.out.print("P2's name: ");
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
        System.out.print(currentPlayer.getUsername() + " starts\n");
        System.out.print("Press enter to roll the dices... \n");
        scanner.nextLine();
        Set<Cell> possibleDestinations = CluedoGame.getInstance().rollDices();
        System.out.print("You have " + possibleDestinations.size() + " possible destinations: \n");
        int number = 1;
        for (Cell destination: possibleDestinations) {
            System.out.println(number + ": " + destination);
            number++;
        }
        System.out.print("Insert x coordinate ");
        int choice = scanner.nextInt();
        System.out.print("Insert y coordinate ");
        int choice2 = scanner.nextInt();

        CluedoGame.getInstance().goToCell(Board.getInstance().getCellXY(choice, choice2));
        scanner.close();

        /* 
        NormalCell cell1 = new NormalCell(5,2);
        Set<Cell> destinations = Board.getInstance().possibleDestinations(cell1, 2);
        System.out.println("Number of destinations: " + destinations.size());
        for (Cell cell : destinations) {
            System.out.println("Possible destination: (" + cell + ")");
        }
        */
    }
}
