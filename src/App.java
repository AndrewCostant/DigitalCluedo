import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import domain.*;
import ui.IO;
import domain.dto.ActionResult;
import domain.dto.DoActionResult;
import domain.dto.RollResult;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println(IO.printDigitalCluedo());
        System.out.println();

        RoomCell startPosition = (RoomCell) Board.getInstance().getCellXY(3, 3);
        ArrayList<Player> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); 
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Player's name: ");
            String name = scanner.nextLine();
            Player player = new Player(name, startPosition);
            players.add(player);
        }

        boolean gameOver = false;
        CluedoGame.getInstance().startGame(players);
        System.out.println();
        IO.printBoardWithPlayers(CluedoGame.getInstance().getPlayers());
        // loop dei turni
        while (!gameOver) {
            System.out.println(
                    "================================" + CluedoGame.getInstance().getCurrentPlayer().getUsername()
                            + "'s turn===========================================");
            System.out.print("Press enter to roll the dices... \n");
            scanner.nextLine();
            System.out.println("This is your hand ");
            System.out.println(CluedoGame.getInstance().getCurrentPlayer().printHandCards());

            System.out.println("\nThese are your known cards ");
            System.out.println(CluedoGame.getInstance().getCurrentPlayer().printKnownCards());

            RollResult rollResult = CluedoGame.getInstance().rollDices();
            Set<Cell> possibleDestinations = rollResult.cells();
            System.out.println("You rolled a: " + rollResult.value());
            System.out.print("\nYou have " + possibleDestinations.size() + " possible destinations: \n");
            int number = 1;
            for (Cell destination : possibleDestinations) {
                System.out.println(number + ": " + destination);
                number++;
            }
            boolean validChoice = false;
            int choice = -1;
            int choice2 = -1;

            System.out.println(CluedoGame.getInstance().getWinningTriplet());

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

            ActionResult typeOfAction = CluedoGame.getInstance().goToCell(Board.getInstance().getCellXY(choice, choice2));
            IO.printBoardWithPlayers(CluedoGame.getInstance().getPlayers());
            System.out.println(typeOfAction);
            /**
             * Versione demo di gestione delle azioni della stanza :)
             */
            SuspectC suspectedPerson = null;
            WeaponC suspectedWeapon = null;

            switch (typeOfAction) {
                case "Normal_Cell":
                    System.out.println("Press Enter to end your turn");
                    scanner.nextLine();
                    break;
                case "Chance_Cell":
                    /**
                     * Leggi l'effetto della carta e chiede eventuali input
                     */
                    System.out.println("You are on a chance cell, press Enter to draw a chanceCard");
                    scanner.nextLine();
                    break;
                default:
                    System.out.println(
                            "You entered a room, make your assumption. Please, note that the suspected room is the room you are into.");

                    System.out.println("Suspects:");
                    System.out.println(CluedoGame.getInstance().printSpecificDeckByType("suspect"));
                    System.out.println("Weapons:");
                    System.out.println(CluedoGame.getInstance().printSpecificDeckByType("weapon"));

                    System.out.println("This is your hand ");
                    System.out.println(CluedoGame.getInstance().getCurrentPlayer().printHandCards());
                    System.out.println("\nThese are your known cards ");
                    System.out.println(CluedoGame.getInstance().getCurrentPlayer().printKnownCards());

                    System.out.println(CluedoGame.getInstance().getWinningTriplet());

                    System.out.print("\nEnter your suspected person:");
                    String person = scanner.nextLine().trim();
                    suspectedPerson = new SuspectC(person);

                    System.out.print("\nEnter your suspected weapon:");
                    String weapon = scanner.nextLine().trim();
                    suspectedWeapon = new WeaponC(weapon);
            }

            DoActionResult result = Board.getInstance().doAction(suspectedPerson, suspectedWeapon);
            System.out.println(result);

            if (result == "Correct guess! You win!") {
                gameOver = true;
            }

            System.out.println("================================================================================================");
        }

        scanner.close();

        /*
         * NormalCell cell1 = new NormalCell(5,2);
         * Set<Cell> destinations = Board.getInstance().possibleDestinations(cell1, 2);
         * System.out.println("Number of destinations: " + destinations.size());
         * for (Cell cell : destinations) {
         * System.out.println("Possible destination: (" + cell + ")");
         * }
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
}
