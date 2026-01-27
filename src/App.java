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
        System.out.println(" 0  | Library =    ?    |         = HomeGym |         =  Lounge = Bathroom|");
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
        System.out.println("    |         |         |         |  P2     |         |         |         |");
        System.out.println(" 3  | DiningR =         |         =  Hall   =         |         |    ?    |");
        System.out.println("    |         |         |         |  P1     |         |         |         |");
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

        boolean gameOver = false;
        CluedoGame.getInstance().startGame(players);

        //loop dei turni
        while(!gameOver) {
            System.out.println("================================"+CluedoGame.getInstance().getCurrentPlayer().getUsername()+"'s turn'===========================================");
            System.out.print("Press enter to roll the dices... \n");
            scanner.nextLine();
            Set<Cell> possibleDestinations = CluedoGame.getInstance().rollDices();
            System.out.print("\nYou have " + possibleDestinations.size() + " possible destinations: \n");
            int number = 1;
            for (Cell destination: possibleDestinations) {
                System.out.println(number + ": " + destination);
                number++;
            }
            boolean validChoice = false;
            int choice = -1;
            int choice2 = -1;

            while (!validChoice) {
                System.out.print("\nInsert x coordinate ");
                choice = scanner.nextInt();
                System.out.print("Insert y coordinate ");
                choice2 = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (checkCoordinates(choice, choice2, possibleDestinations)) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid coordinates. Please choose again.");
                }
            }

            CluedoGame.getInstance().goToCell(Board.getInstance().getCellXY(choice, choice2));
            printBoardWithPlayers(CluedoGame.getInstance().getPlayers()); 
            CluedoGame.getInstance().endTurn();
            System.out.println("===========================================================================");
        }
        
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

    public static boolean checkCoordinates(int x, int y, Set<Cell> possibleDestinations) {
        for (Cell cell : possibleDestinations) {
            if (cell.getX() == x && cell.getY() == y) {
                return true;
            }
        }
        return false;
    }



    /**
     * Stampa la mappa ASCII con i nomi dei player nelle loro posizioni attuali.
     * @param players Lista dei giocatori da stampare sulla mappa.
     */
    public static void printBoardWithPlayers(List<Player> players) {

        String[] map = {
            "    y    0         1         2         3         4         5         6   ",
            " x  +---------+---------+---------+---------+---------+---------+---------+",
            "    |         |         |         |         |         |         |         |",
            " 0  | Library =    ?    |         = HomeGym |         =  Lounge = Bathroom|",
            "    |         |         |         |         |         |         |         |",
            "    +----║----+---------+---------+---------+---------+---------+----║----+",
            "    |         |         |         |         |         |         |         |",
            " 1  | Kitchen |         |         |         |         |         |    ?    |",
            "    |         |         |         |         |         |         |         |",
            "    +----║----+---------+---------+---------+---------+---------+---------+",
            "    |         |         |         |         |         |         |         |",
            " 2  |         =         |    ?    |         |         |         |  Garden |",
            "    |         |         |         |         |         |         |         |",
            "    +---------+---------+---------+----║----+---------+---------+----║----+",
            "    |         |         |         |         |         |         |         |",
            " 3  | DiningR =         |         =  Hall   =         |         |    ?    |",
            "    |         |         |         |         |         |         |         |",
            "    +----║----+---------+---------+----║----+---------+---------+---------+",
            "    |         |         |         |         |         |         |         |",
            " 4  |         |    ?    |         |         |         |         |         |",
            "    |         |         |         |         |         |         |         |",
            "    +---------+----║----+---------+---------+---------+---------+---------+",
            "    |         |         |         |         |         |         |         |",
            " 5  | BedRoom =  Study  =         |    ?    |         |    ?    =  Ghouse |",
            "    |         |         |         |         |         |         |         |",
            "    +----║----+---------+---------+----║----+---------+---------+----║----+",
            "    |         |         |         |         |         |         |         |",
            " 6  |    ?    |         |         | Balcony |         |         |         |",
            "    |         |         |         |         |         |         |         |",
            "    +---------+---------+---------+---------+---------+---------+---------+"
        };

        StringBuilder[] output = new StringBuilder[map.length];
        for (int i = 0; i < map.length; i++) {
            output[i] = new StringBuilder(map[i]);
        }

        // raggruppa player per cella
        Map<String, List<String>> playersPerCell = new HashMap<>();
        for (Player p : players) {
            int x = p.getPosition().getX();
            int y = p.getPosition().getY();
            playersPerCell
                .computeIfAbsent(x + "," + y, k -> new ArrayList<>())
                .add(p.getUsername());
        }

        // scrittura player: SEMPRE riga bassa della cella
        for (var entry : playersPerCell.entrySet()) {

            String[] xy = entry.getKey().split(",");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);

            int row = asciiBottomRowForCell(x);
            int col = asciiColForCell(y);

            String text = String.join(" ", entry.getValue());

            for (int i = 0; i < text.length(); i++) {
                if (col + i < output[row].length()) {
                    output[row].setCharAt(col + i, text.charAt(i));
                }
            }
        }

        for (StringBuilder line : output) {
            System.out.println(line);
        }
    }

    private static int asciiBottomRowForCell(int x) {
        // riga bassa della cella
        return 4 + x * 4;
    }

    private static int asciiColForCell(int y) {
        return 5 + y * 10;
    }
}
