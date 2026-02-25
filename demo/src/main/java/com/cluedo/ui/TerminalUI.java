package com.cluedo.ui;

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

import com.cluedo.config.*;
import com.cluedo.controller.GameController;
import com.cluedo.domain.*;
import com.cluedo.domain.dto.ActionResult;
import com.cluedo.domain.dto.DoActionResult;
import com.cluedo.domain.dto.RollResult;

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

    /*************************************************************SETUP INSTANCE*************************************************************/
    /**
     * Initialize the welcome message by reading it from a file specified in GameConfig. The file is expected to be in the resources folder of the project.
     * @throws FileNotFoundException if the file specified in GameConfig.INTESTAZIONE is not found in the resources folder.
     */
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

    /**
     * Initialize the ASCII map by reading it from a file specified by the current game mode's factory. The file is expected to be in the resources folder of the project.
     * @throws IOException
     */
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

    /*************************************************************PRINT SETUP GAME*************************************************************/
    /**
     * Print the welcome message to the console. The welcome message is stored in the 'welcome' ArrayList, which is initialized by reading from a file in the resources folder. Each line of the welcome message is printed on a new line in the console.
     */
    public void printWelcome() {
        for (String line : welcome) {
            System.out.println(line);
        }
    }

    /*************** GAME MODE ****************/

    public AbstractGameModeFactory askGameMode() throws FileNotFoundException {
        String[] gameMode = GameModeRegistry.getAvailableModes().toArray(new String[0]);
        
        System.out.println("Choose game mode:");
        int i = 1;
        for (String mode : gameMode) {
            System.out.println(i + ". " + mode);
            i++;
        }
        int choice = -1;
        while (choice < 1 || choice > gameMode.length) {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (choice < 1 || choice > gameMode.length) {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        System.out.println();
        System.out.println("You chose: " + gameMode[choice - 1]);

        return GameModeRegistry.getMode(gameMode[choice - 1].toString());
    }  
        
    /**
     * Display the destination cell for the Speed game mode. 
     * This method takes a RollResult object as input, which contains the value rolled and the set of possible destination cells.
     * @param rollResult
     */
    public void displaySpeedDestination(RollResult rollResult) {
        System.out.println("Press enter to move to " + rollResult.cells().iterator().next() + " .");
        scanner.nextLine();
    }

    /**
     * Ask the user to input the number of players and their names, ensuring that the number of players is within the limits defined in GameConfig. The method returns a list of player names.
     * @return a list of player names input by the user.
     */
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
     * Print the ASCII map of the game board, including the players' names in their respective positions. 
     * The method takes a list of Player objects as input, where each Player object contains information about the player's name and current position on the board. 
     * The players' names are printed in the lower row of the cell they are currently occupying. If multiple players occupy the same cell, their names are printed separated by spaces.
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

    private static int asciiBottomRowForCell(int x) {
        // riga bassa della cella
        return 4 + x * 4;
    }

    private static int asciiColForCell(int y) {
        return 5 + y * 10;
    }

    /**********************************************TURN*****************************************************/
    /**
     * Start the turn for the given player. This method prints the player's name, their hand cards, and their known cards. 
     * It then prompts the player to press enter to roll the dice. The method is called at the beginning of each player's turn in the game loop.
     * @param player
     */
    public void startTurn(Player player){
        System.out.println("================================" + player.getUsername() + "'s turn===========================================");
        System.out.println("This is your hand ");
        System.out.println(player.printHandCards());
        System.out.println("\nThese are your known cards ");
        System.out.println(player.printKnownCards());
        System.out.print("Press enter to roll the dices... \n");
        scanner.nextLine();
    } 

    /**
     * Print the result of rolling the dice, including the value rolled and the possible destination cells based on the roll result. 
     * The method takes a RollResult object as input, which contains the value rolled and the set of possible destination cells. 
     * Each possible destination cell is printed with a corresponding number for selection in the next step of the game.
     * @param rollResult
     */
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

    /**
     * Check if the given coordinates (x, y) are present in the set of possible destination cells. 
     * @param x
     * @param y
     * @param possibleDestinations
     * @return
     */
    private boolean checkCoordinates(int x, int y, Set<Cell> possibleDestinations) {
        for (Cell cell : possibleDestinations) {
            if (cell.getX() == x && cell.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ask the user to input the coordinates (x, y) of their chosen destination cell based on the possible destinations provided in the RollResult.
     * @param rollResult
     * @return
     */
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

    /***************ACTIONS IN CELLS****************/

    public void displayNormalCellAction(){
        System.out.println("You entered a normal cell, you can't do any action...");
    }

    /**
     * Display the action for a chance cell. 
     * @param card
     */
    public void displayChanceCellAction(Card card){
        System.out.println("You are on a chance cell, press Enter to draw a chanceCard");
        scanner.nextLine();
        System.out.println("You drew the card: " + card.getName());
    }

    /**
     * Display the action for a gambling room cell. 
     * @param actionResult
     */
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

    /**
     * Display the action for a room cell, which allows the player to make an assumption. 
     * @param handCards
     * @param knownCards
     * @param suspectCards
     * @param weaponCards
     * @return
     */
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
        Boolean validInput = true;
        while (validInput) {
            System.out.print("Enter your suspected person:");
            String person = scanner.nextLine().trim();
            if (!GameController.checkIfExist(person)) {
                System.out.println("Invalid person name.");
                continue;
            } else {
                Card suspectedPerson = (Card) CardFactory.createCard("suspect", person);
                 if (suspectedPerson != null) {
                    assumption.add(suspectedPerson);
                    validInput = false;
                } else {
                    System.out.println("Invalid person. Please try again.");
                }
            }
        }
        validInput = true;
        while (validInput) {
            System.out.print("Enter your suspected weapon:");
            String weapon = scanner.nextLine().trim();
            if (!GameController.checkIfExist(weapon)) {
                System.out.println("Invalid weapon name.");
                continue;
            } else {
                Card suspectedWeapon = (Card) CardFactory.createCard("weapon", weapon);
                 if (suspectedWeapon != null) {
                    assumption.add(suspectedWeapon);
                    validInput = false;
                } else {
                    System.out.println("Invalid weapon. Please try again.");
                }
            }
        }
        System.out.println();
        return assumption;
    }

    /**
     * Print the winner of the game. This method is called when the game ends and a player has won. 
     * It takes a Player object as input and prints a congratulatory message with the player's name.
     * @param player
     */
    public void printWinner(Player player){
        System.out.println("Congratulations " + player.getUsername() + "! You won the game!");
    }

    public void printNoCardsShown(){
        System.out.println("No one could show you a card that matches your assumption.");
    }

    /**
     * Print the cards that were shown to the player as a result of their assumption. 
     * This method takes a list of Card objects as input and prints the name of each card shown to the player.
     * @param cardsShown
     */
    public void printCardsShown(ArrayList<Card> cardsShown){
        System.out.println("You have discovered the following cards: ");
        for (Card card : cardsShown) {
            System.out.println("- " + card.getName());
        }
        System.out.println("In the next turns, try to find out if these cards are in the winning solution or if they are in someone's hand.");
    }

    /**
     * Print the effect of a chance card that was drawn by the player. 
     * This method takes a DoActionResult object as input, which contains information about the effect of the chance card.
     * @param doActionResult
     */
    public void printEffect(DoActionResult doActionResult){
        System.out.println("You have discovered the following effect: " + doActionResult.getEffect().getDescription());
    }

    /**
     * Show the card that was peeked at by the player as a result of their action in a room cell.
     * @param doActionResult
     */
    public void showPeekedCard(DoActionResult doActionResult){ 
        System.out.println("You peeked at the card: " + doActionResult.getCardsShown().get(0).getName());
    }

    public void displayEndTurn(){
        System.out.println("Press Enter to end your turn");
        scanner.nextLine();
    }
}
