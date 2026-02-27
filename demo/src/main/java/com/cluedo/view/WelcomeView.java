package com.cluedo.view;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.cluedo.config.GameConfig;
import com.cluedo.config.GameEvent;
import com.cluedo.config.GameModeRegistry;
import com.cluedo.controller.GameController;

public class WelcomeView implements GameObserver {

    private ArrayList<String> welcome = new ArrayList<>();
    private InputView input;
    private GameController controller;
    

    public WelcomeView(GameController controller) throws FileNotFoundException {
        this.controller = controller;
        input = new InputView();
        try {
            initializeWelcome();
        } catch (FileNotFoundException e) {
            System.err.println("Error initializing welcome message: " + e.getMessage());
        }
    }

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
    
    @Override
    public void update(GameEvent event) throws FileNotFoundException {
        if (event == GameEvent.WELCOME) {
            printWelcome();
            askGameMode();
        }
        if (event == GameEvent.SET_PLAYERS) {
            initializePlayers();
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
            choice = input.askInt();
            if (choice < 1 || choice > gameMode.length) {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        System.out.println();
        System.out.println("You chose: " + gameMode[choice - 1]);
        controller.setGameMode(gameMode[choice - 1]);
    } 


    /**
     * Ask the user to input the number of players and their names, ensuring that the number of players is within the limits defined in GameConfig. The method returns a list of player names.
     */
    public void initializePlayers(){
        ArrayList<String> players = new ArrayList<>();
        int numPlayers = -1;
        while (numPlayers < GameConfig.MIN_PLAYERS || numPlayers > GameConfig.MAX_PLAYERS) {
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
        controller.setPlayers(players);
    }


    
}
