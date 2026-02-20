package ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import config.GameConfig;
import domain.*;
import domain.dto.ActionResult;
import domain.dto.DoActionResult;
import domain.dto.RollResult;

public class TerminalUI {
    
    private static volatile TerminalUI instance;
    private Scanner scanner;

    StringBuilder[] output;
    ArrayList<String> welcome = new ArrayList<>();

    private TerminalUI() throws FileNotFoundException {
        scanner = new Scanner(System.in);
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
        String path = GameConfig.INTESTAZIONE;;

        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }
		try (Scanner sc = new Scanner(is)) {
            while (sc.hasNextLine()) {
                welcome.add(sc.nextLine());
            }
        }
        catch (Exception e) {
            throw new FileNotFoundException("File " + path + " not found");
        }
    }

    public void initializeMap() throws IOException {
        String path = CluedoGame.getInstance()
                .getGameModeFactory()
                .getUiMapPath();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(path);

        if (is == null) {
            throw new RuntimeException("File not found: " + path);
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        output = new StringBuilder[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            output[i] = new StringBuilder(lines.get(i));
        }
    }

    public ArrayList<String> initializePlayers(){
        ArrayList<String> players = new ArrayList<>();
        int numPlayers = -1;
        while (numPlayers < GameConfig.MIN_PLAYERS || numPlayers > GameConfig.MAX_PLAYERS) {
            System.out.print("Enter number of players (" + GameConfig.MIN_PLAYERS + "-" + GameConfig.MAX_PLAYERS + "): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (numPlayers < GameConfig.MIN_PLAYERS || numPlayers > GameConfig.MAX_PLAYERS) {
                System.out.println("Invalid number of players. Please try again.");
            }
        }
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Player's name: ");
            String name = scanner.nextLine();
            players.add(name);
        }
        return players;
    }

    /**
     * Stampa la mappa ASCII con i nomi dei player nelle loro posizioni attuali.
     * 
     * @param players Lista dei giocatori da stampare sulla mappa.
     */
    public void printBoardWithPlayers(ArrayList<Player> players) {
        StringBuilder[] outputCopy = new StringBuilder[output.length];
        for (int i = 0; i < output.length; i++) {
            outputCopy[i] = new StringBuilder(output[i].toString());
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
                if (col + i < outputCopy[row].length()) {
                    outputCopy[row].setCharAt(col + i, text.charAt(i));
                }
            }
        }
        for (StringBuilder line : outputCopy) {
            System.out.println(line);
        }
    }

    public void printWelcome() {
        for (String line : welcome) {
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

    /**********************************************TURN*****************************************************/
    public void startTurn(Player player){
        System.out.println("================================" + player.getUsername() + "'s turn===========================================");
        System.out.println("This is your hand ");
        System.out.println(player.printHandCards());
        System.out.println("\nThese are your known cards ");
        System.out.println(player.printKnownCards());
        System.out.print("Press enter to roll the dices... \n");
        scanner.nextLine();
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
        boolean validChoice = false;
        int choice = -1;
        int choice2 = -1;

        while (!validChoice) {
            System.out.print("\nInsert x coordinate ");
            choice = scanner.nextInt();
            System.out.print("Insert y coordinate ");
            choice2 = scanner.nextInt();
            if (checkCoordinates(choice, choice2, rollResult.cells())) {
                validChoice = true;
            } else {
                System.out.println("Invalid coordinates. Please try again.");
            }
        }
        System.out.println();
        scanner.nextLine(); // consume the newline character after nextInt()
        ArrayList<Integer> result = new ArrayList<>();
        result.add(choice);
        result.add(choice2);
        return result;
    }

    public void displayNormalCellAction(){
        System.out.println("You entered a normal cell, you can't do any action...");
    }

    public void displayChanceCellAction(Card card){
        System.out.println("You are on a chance cell, press Enter to draw a chanceCard");
        scanner.nextLine();
        System.out.println("You drew the card: " + card.getName());
    }

    public void displayGamblingRoomAction(ActionResult actionResult){
        System.out.println("You are on a gambling room cell, press Enter to gamble");
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
    }

    public ArrayList<Card> displayRoomAction(ArrayList<Card> handCards, Map<Card,String> knownCards, String suspectCards, String weaponCards){
        ArrayList<Card> assumption = new ArrayList<>();
        System.out.println();
        System.out.println("You entered a room, make your assumption. \nPlease, note that the suspected room is the room you are into.");
        System.out.println("This is your hand ");
        System.out.println(handCards);
        System.out.println();
        System.out.println("These are your known cards ");
        System.out.println(knownCards);
        System.out.println();
        System.out.println("These are the suspect cards: ");
        System.out.println(suspectCards);
        System.out.println("These are the weapon cards: ");
        System.out.println(weaponCards);
        System.out.print("Enter your suspected person:");
        String person = scanner.nextLine().trim();
        Card suspectedPerson = (Card) CardFactory.createCard("suspect", person);
        System.out.print("Enter your suspected weapon:");
        String weapon = scanner.nextLine().trim();
        Card suspectedWeapon = (Card) CardFactory.createCard("weapon", weapon);
        assumption.add(suspectedPerson);
        assumption.add(suspectedWeapon);
        System.out.println();
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
        scanner.nextLine();
    }

    /*************** GAME MODE ****************/

    public int askGameMode() {
        System.out.println("Choose game mode:");
        System.out.println("1. Classic");
        System.out.println("2. Speed");
        // in futuro, altre modalità
        int choice = -1;
        while (choice < 1 || choice > 2) {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (choice < 1 || choice > 2) {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println();
        if (choice == 1) {
            System.out.println("You chose Classic mode.");
        } else {
            System.out.println("You chose Speed mode.");
        }
        return choice;
    }  
    
    public void displaySpeedDestination(RollResult rollResult) {
        System.out.println("Press enter to move to " + rollResult.cells().iterator().next() + " .");
        scanner.nextLine();
    }

}
