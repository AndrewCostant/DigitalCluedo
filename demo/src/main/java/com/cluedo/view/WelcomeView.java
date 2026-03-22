package com.cluedo.view;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.cluedo.config.GameConfig;
import com.cluedo.config.GameEvent;
import com.cluedo.config.GameModeRegistry;
import com.cluedo.controller.GameController;

public class WelcomeView {

    private ArrayList<String> welcome = new ArrayList<>();
    private InputView input;
    private GameController controller;
    private EventHandler eventHandler;
    

    public WelcomeView(GameController controller) throws FileNotFoundException {
        this.controller = controller;
        input = new InputView();
        eventHandler = new EventHandler();
        
        // Register event-specific handlers
        eventHandler.on(GameEvent.WELCOME, event -> {
            printWelcome();
            askGameMode();
        });
        
        eventHandler.on(GameEvent.SET_PLAYERS, event -> {
            initializePlayers();
        });
        
        try {
            initializeWelcome();
        } catch (FileNotFoundException e) {
            System.err.println("Error initializing welcome message: " + e.getMessage());
        }
    }
    
    /*********************************************************FUNCTIONAL METHODS******************************************************************/
    public static void printSeparator() {
        System.out.println("-.".repeat(49));
    }

    public static void space() {
        System.out.println();
    }

    /**
     * Returns the event handler for this view.
     * Use this to register the view as an observer to the game.
     * @return the event handler
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    /*********************************************************START GAME METHODS******************************************************************/

    /**
     * Initialize the welcome message by reading it from a file specified in GameConfig. The file is expected to be in the resources folder of the project.
     * @throws FileNotFoundException if the file specified in GameConfig.INTESTAZIONE is not found in the resources folder.
     */
    private void initializeWelcome() throws FileNotFoundException{
        String path = GameConfig.INTESTAZIONE;

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
     * Print the welcome message to the console. The welcome message is stored in the 'welcome' ArrayList, which is initialized by reading from a file in the resources folder. Each line of the welcome message is printed on a new line in the console.
     */
    public void printWelcome() {
        for (String line : welcome) {
            System.out.println(line);
        }
    }

    public void askGameMode() throws FileNotFoundException {
        ArrayList<String> gameMode = GameModeRegistry.getAvailableModes();
        printSeparator();
        space();   
        System.out.println("Which game mode do you want to play?");
        int i = 1;
        for (String mode : gameMode) {
            System.out.println(i + ". " + mode);
            i++;
        }
        space();
        int choice = -1;
        while (choice < 1 || choice > gameMode.size()) {
            System.out.print("Enter your choice (1 or 2): ");
            choice = input.askInt();
            if (choice < 1 || choice > gameMode.size()) {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        space();
        printSeparator();
        space();
        System.out.println("You chose: " + gameMode.get(choice - 1));
        space();
        printSeparator();
        controller.setGameMode(GameModeRegistry.getMode(choice - 1));
    } 


    /**
     * Ask the user to input the number of players and their names, ensuring that the number of players is within the limits defined in GameConfig. The method returns a list of player names.
     * @throws InterruptedException 
     */
    public void initializePlayers() throws InterruptedException{
        ArrayList<String> players = new ArrayList<>();
        int numPlayers = -1;
        while (numPlayers < GameConfig.MIN_PLAYERS || numPlayers > GameConfig.MAX_PLAYERS) {
            space();
            System.out.print("Enter number of players (" + GameConfig.MIN_PLAYERS + "-" + GameConfig.MAX_PLAYERS + "): ");
            numPlayers = input.askInt();
            if (numPlayers < GameConfig.MIN_PLAYERS || numPlayers > GameConfig.MAX_PLAYERS) {
                System.out.println("Invalid number of players. Please try again.");
            }
        }
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Player's name: ");
            String name = input.askString();
            players.add(name);
        }
        space();
        LoadingBar.showLoadingBar(30);
        controller.setPlayers(players);
    }    
}
