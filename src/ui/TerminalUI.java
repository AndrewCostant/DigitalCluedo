package ui;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import domain.*;
import domain.dto.ActionResult;
import domain.dto.DoActionResult;
import domain.dto.RollResult;

public class TerminalUI {
    
    private static volatile TerminalUI instance;

    StringBuilder[] output;
    ArrayList<String> welcome;

    private TerminalUI() throws FileNotFoundException {
        this.initializeMap();
        this.initializeWelcome();
    }

    public static TerminalUI getInstance() throws FileNotFoundException {
        if (instance == null) {
            instance = new TerminalUI();
        }
        return instance;
    }

    /*************************************************************PRINT SETUP GAME*************************************************************/
    private void initializeWelcome() throws FileNotFoundException{
        String path = "utility/intestazione.txt";

        InputStream is = Board.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }
		try (Scanner sc = new Scanner(is)) {
            while (sc.hasNextLine()) {
                welcome.add(sc.nextLine());
            }
        }
        catch (Exception e) {
            throw new FileNotFoundException("File intestazione.txt not found");
        }
    }

    private void initializeMap() {
        String path = "utility/map.txt";
        InputStream is = Board.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }
        String[] map = new String[30];
		try (Scanner sc = new Scanner(is)) {
            for (int i = 0; i < map.length; i++) {
                if (sc.hasNextLine()) {
                    map[i] = sc.nextLine();
                } else {
                    throw new RuntimeException("File has fewer lines than expected: " + path);
                }
            }
        }
        output = new StringBuilder[map.length];
        for (int i = 0; i < map.length; i++) {
            output[i] = new StringBuilder(map[i]);
        }
    }

    public ArrayList<String> initializePlayers(){
        ArrayList<String> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); 
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Player's name: ");
            String name = scanner.nextLine();
            players.add(name);
        }
        scanner.close();
        return players;
    }

    /**
     * Stampa la mappa ASCII con i nomi dei player nelle loro posizioni attuali.
     * 
     * @param players Lista dei giocatori da stampare sulla mappa.
     */
    public void printBoardWithPlayers(ArrayList<Player> players) {

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

    public void printWelcome() {
        for (String line : welcome) {
            System.out.print(line);
        }
    }

    private static int asciiBottomRowForCell(int x) {
        // riga bassa della cella
        return 4 + x * 4;
    }

    private static int asciiColForCell(int y) {
        return 5 + y * 10;
    }

    /**********************************************TURN*****************************************************/
    public void startTurn(Player player){
        System.out.println("================================" + player.getUsername() + "'s turn===========================================");
        System.out.println("This is your hand ");
        System.out.println(player.printHandCards());
        System.out.println("\nThese are your known cards ");
        System.out.println(player.printKnownCards());
        System.out.print("Press enter to roll the dices... \n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    } 

    public void rollResult(RollResult  rollResult){
        System.out.println("You rolled a: " + rollResult.value());
        System.out.print("\nYou have " + rollResult.cells().size() + " possible destinations: \n");
        int number = 1;
        Set<Cell> possibleDestinations = rollResult.cells();
        for (Cell destination : possibleDestinations) {
            System.out.println(number + ": " + destination);
            number++;
        }
    }

    private boolean checkCoordinates(int x, int y, Set<Cell> possibleDestinations) {
        for (Cell cell : possibleDestinations) {
            if (cell.getX() == x && cell.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> askDestination(RollResult rollResult){
        Scanner scanner = new Scanner(System.in);
        boolean validChoice = false;
        int choice = -1;
        int choice2 = -1;

        while (!validChoice) {
            System.out.print("\nInsert x coordinate ");
            choice = scanner.nextInt();
            System.out.print("\nInsert y coordinate ");
            choice2 = scanner.nextInt();
            if (checkCoordinates(choice, choice2, rollResult.cells())) {
                validChoice = true;
            } else {
                System.out.println("Invalid coordinates. Please try again.");
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(choice);
        result.add(choice2);
        scanner.close();
        return result;
    }

    public void displayNormalCellAction(){
        System.out.println("You entered a normal cell, press Enter to end your turn");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }

    public void displayChanceCellAction(Card card){
        System.out.println("You are on a chance cell, press Enter to draw a chanceCard");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("You drew the card: " + card.getName());
        scanner.close();
    }

    public void displayGamblingRoomAction(ActionResult actionResult){
        System.out.println("You are on a gambling room cell, press Enter to gamble");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int number = actionResult.getValue();
        int condition = ((GamblingRoom) actionResult.getCell()).getCondition();
        if (number == condition) {
            System.out.println("Congratulations! You rolled a " + number + " and won the gamble!");
            System.out.println("The winning card is: " + actionResult.getCard().getName());
        } else {
            System.out.println("You rolled a " + number + " and lost the gamble.");
            System.out.println("The card you showed is: " + actionResult.getCard().getName());
        }
        scanner.close();
    }

    public ArrayList<Card> displayRoomAction(ArrayList<Card> handCards, Map<Card,String> knownCards, String suspectCards, String weaponCards){
        Scanner scanner = new Scanner(System.in);
        ArrayList<Card> assumption = new ArrayList<>();
        System.out.println("You entered a room, make your assumption. Please, note that the suspected room is the room you are into.");
        System.out.println("This is your hand ");
        System.out.println(handCards);
        System.out.println();
        System.out.println("\nThese are your known cards ");
        System.out.println(knownCards);
        System.out.println();
        System.out.println("\nThese are the suspect cards: ");
        System.out.println(suspectCards);
        System.out.println();
        System.out.println("\nThese are the weapon cards: ");
        System.out.println(weaponCards);
        System.out.println();
        System.out.print("\nEnter your suspected person:");
        String person = scanner.nextLine().trim();
        Card suspectedPerson = new SuspectC(person);
        System.out.print("\nEnter your suspected weapon:");
        String weapon = scanner.nextLine().trim();
        Card suspectedWeapon = new WeaponC(weapon);
        scanner.close();
        assumption.add(suspectedPerson);
        assumption.add(suspectedWeapon);
        return assumption;
    }

    public void printWinner(Player player){
        System.out.println("Congratulations " + player.getUsername() + "! You won the game!");
    }

    public void printNoCardsShown(){
        System.out.println("No one could show you a card that matches your assumption.");
    }

    public void printCardsShown(ArrayList<Card> cardsShown){
        System.out.println("You have discovered the following cards: ");
        for (Card card : cardsShown) {
            System.out.println("- " + card.getName());
        }
        System.out.println("In the next turns, try to find out if these cards are in the winning solution or if they are in someone's hand.");
    }

    public void printEffect(DoActionResult doActionResult){
        System.out.println("You have discovered the following effect: " + doActionResult.getEffect().getDescription());
    }

    public void showPeekedCard(DoActionResult doActionResult){ 
        System.out.println("You peeked at the card: " + doActionResult.getCardsShown().get(0).getName());
    }

    public void displayEndTurn(){
        System.out.println("Press Enter to end your turn");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }

}
