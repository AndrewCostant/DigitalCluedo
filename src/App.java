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
        System.out.println();
        System.out.println("Creating the board...");
        System.out.println();
        System.out.println("The board layout is as follows:");
        System.out.println();
        System.out.println("    y    0         1         2         3         4         5         6   ");
        System.out.println(" x  +---------+---------+---------+---------+---------+---------+---------+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 0  | Library =    ?    |         =   H G   |         =  Lounge = Bathroom|");
        System.out.println("    |         |         |         |         |         |         |         |");  
        System.out.println("    +----‖----+---------+---------+---------+---------+---------+----‖----+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 1  | Kitchen |         |         |         |         |         |    ?    |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +----‖----+---------+---------+---------+---------+---------+---------+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 2  |         =         |    ?    |         |         |         |  Garden |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +---------+---------+---------+----‖----+---------+---------+----‖----+"); 
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 3  | DiningR =         |         =  Hall   =         |         |    ?    |");
        System.out.println("    |         |         |         |  P1  P2 |         |         |         |");
        System.out.println("    +----‖----+---------+---------+----‖----+---------+---------+---------+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 4  |         |    ?    |         |         |         |         |         |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +---------+----‖----+---------+---------+---------+---------+---------+");  
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 5  | BedRoom =  Study  =         |    ?    |         |    ?    =  Ghouse |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +----‖----+---------+---------+----‖----+---------+---------+----‖----+");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println(" 6  |    ?    |         |         | Balcony |         |         |         |");
        System.out.println("    |         |         |         |         |         |         |         |");
        System.out.println("    +---------+---------+---------+---------+---------+---------+---------+");    
        System.out.println();
        System.out.println("Legend: '=' and '‖' doors, '?' chances.");
        System.out.println();
        System.out.println("===========================================================================");
        System.out.println();
        System.out.println("In questa demo ci sono 2 giocatori: P1 e P2. Entrambi iniziano nella Hall e,");
        System.out.println("a turno, possono lanciare i dadi e muoversi nella mappa.");
        System.out.println("Enjoy the demo!");


        NormalCell cell1 = new NormalCell(5,2);
        Set<Cell> destinations = Board.getInstance().possibleDestinations(cell1, 2);
        System.out.println("Number of destinations: " + destinations.size());
        for (Cell cell : destinations) {
            System.out.println("Possible destination: (" + cell + ")");
        }
    }
}
