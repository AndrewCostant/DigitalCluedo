package com.cluedo.view;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.cluedo.config.GameEvent;
import com.cluedo.controller.GameController;
import com.cluedo.domain.Board;
import com.cluedo.domain.Card;
import com.cluedo.domain.CardFactory;
import com.cluedo.domain.Cell;
import com.cluedo.domain.ClassicGameModeFactory;
import com.cluedo.domain.CluedoGame;
import com.cluedo.domain.GamblingRoom;
import com.cluedo.domain.PeekCard;
import com.cluedo.domain.Player;
import com.cluedo.domain.RoomCell;
import com.cluedo.domain.SpeedGameModeFactory;
import com.cluedo.domain.Turn;
import com.cluedo.domain.dto.ActionResult;
import com.cluedo.domain.dto.DoActionResult;
import com.cluedo.domain.dto.RollResult;

public class TurnView implements GameObserver {
    
    private GameController controller;
    private BoardView boardView;
    private InputView input;
    private Turn currentTurn;

    public TurnView(GameController controller, InputView input) {
        this.boardView = new BoardView();
        this.controller = controller;
        this.input = input;
    }

    @Override
    public void update(GameEvent event) throws Exception {
        if(!boardView.isInitialized()) {
            boardView.initializeMap();
        }
        if (event == GameEvent.ROLL_DICES) {
            startTurn();
        }
        if (event == GameEvent.SELECT_DESTINATION) {
            this.currentTurn = CluedoGame.getInstance().getCurrentTurn();
            boardView.printBoardWithPlayers(CluedoGame.getInstance().getPlayers());
            rollResult();
            Cell destination = null;
            //depending on the game mode, ask the player to choose the destination cell in different ways
            if (CluedoGame.getInstance().getGameModeFactory() instanceof ClassicGameModeFactory) {
                destination = askDestination();  
                
            }
            else if (CluedoGame.getInstance().getGameModeFactory() instanceof SpeedGameModeFactory) {
                displaySpeedDestination();
                destination = this.currentTurn.getRollResult().cells().iterator().next();
            }
            controller.goToCell(destination);
        }
        if (event == GameEvent.DO_ACTION) {
            ActionResult actionResult = this.currentTurn.getActionResult();
            ArrayList<Card> assumption = displayAction(actionResult);
            controller.doAction(assumption);
        }
        if (event == GameEvent.END_TURN) {
            DoActionResult doActionResult = this.currentTurn.getDoActionResult();
            displayDoActionResult(doActionResult);
            displayEndTurn(doActionResult);
            controller.endTurn(doActionResult);
        }
    }

    public static void printSeparator() {
        System.out.println("-.".repeat(49));
    }

    public static void printSeparatorTurn() {
        System.out.print("=".repeat(70));
    }

    // ROLL_DICES


    /**
     * Start the turn for the given player. This method prints the player's name, their hand cards, and their known cards. 
     * It then prompts the player to press enter to roll the dice. The method is called at the beginning of each player's turn in the game loop.
     * @param player
     */
    public void startTurn(){
        Player player = CluedoGame.getInstance().getCurrentPlayer();
        System.out.println();
        printSeparatorTurn();
        System.out.print(" " + player.getUsername().toUpperCase() + "'s turn ");
        printSeparatorTurn();
        System.out.println();
        System.out.println();
        System.out.println("This is your hand ");
        System.out.println(player.printHandCards());
        System.out.println("\nThese are your known cards ");
        System.out.println(player.printKnownCards());
        System.out.print("Press enter to roll the dices... \n");
        input.pressButton();
        controller.rollDices();
    } 

    // SELECT_DESTINATION

     /**
     * Print the result of rolling the dice, including the value rolled and the possible destination cells based on the roll result. 
     * The method takes a RollResult object as input, which contains the value rolled and the set of possible destination cells. 
     * Each possible destination cell is printed with a corresponding number for selection in the next step of the game.
     */
    public void rollResult(){
        RollResult rollResult = this.currentTurn.getRollResult();
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
    public Cell askDestination(){
        RollResult rollResult = this.currentTurn.getRollResult();
        boolean validChoice = false;
        int choice = -1;
        int choice2 = -1;

        while (!validChoice) {
            System.out.print("\nInsert x coordinate ");
            choice = input.askInt();
            System.out.print("Insert y coordinate ");
            choice2 = input.askInt();
            if (checkCoordinates(choice, choice2, rollResult.cells())) {
                validChoice = true;
            } else {
                System.out.println("Invalid coordinates. Please try again.");
            }
        }
        System.out.println();
        return Board.getInstance().getCellXY(choice, choice2);
    }

    /**
     * Display the destination cell for the Speed game mode. 
     * This method takes a RollResult object as input, which contains the value rolled and the set of possible destination cells.
     * @param rollResult
     */
    public void displaySpeedDestination() {
        RollResult rollResult = this.currentTurn.getRollResult();
        printSeparator();
        System.out.println("Press enter to move to " + rollResult.cells().iterator().next() + " .");
        input.pressButton();
    }

    //DO_ACTION

    private ArrayList<Card> displayAction(ActionResult actionResult) throws Exception {
        ArrayList<Card> assumption = new ArrayList<>();
        switch (actionResult.getType()) {
            case "NORMAL_CELL":
                displayNormalCellAction();
                break;
            case "CHANCE_CELL":
                displayChanceCellAction(actionResult.getCard());
                break;
            case "GAMBLING_ROOM_CELL":
                displayGamblingRoomAction(actionResult);
            default:
                assumption = displayRoomAction();
        }
        return assumption;
    }

    public void displayNormalCellAction(){
        printSeparator();
        System.out.println();
        System.out.println("You are on a Normal Cell, you can't do any action...");
        System.out.println();
        printSeparator();
        
    }

    /**
     * Display the action for a chance cell. 
     * @param card
     */
    public void displayChanceCellAction(Card card){
        printSeparator();
        System.out.println();
        System.out.println("You are on a Chance Cell, press Enter to draw a chanceCard");
        input.pressButton();
        printSeparator();
        System.out.println();
        System.out.println("You drew the card: " + card.getName());
    }

    /**
     * Display the action for a gambling room cell. 
     * @param actionResult
     */
    public void displayGamblingRoomAction(ActionResult actionResult){
        System.out.println();
        System.out.println("You are on a Gambling Room called " + actionResult.getCell().getType() + ", press Enter to gamble");
        input.pressButton();
        int number = actionResult.getValue();
        int condition = ((GamblingRoom) actionResult.getCell()).getCondition();
        if (number == condition) {
            printSeparator();
            System.out.println("Congratulations! You rolled a " + number + " and won the gamble!");
            System.out.println("The winning card is: " + actionResult.getCard().getName());
            printSeparator();
        } else {
            printSeparator();
            System.out.println("You rolled a " + number + " and lost the gamble.");
            System.out.println("The card you showed is: " + actionResult.getCard().getName());
            printSeparator();
        }
    }

    /**
     * Display the action for a room cell, which allows the player to make an assumption. 
     * @param handCards
     * @param knownCards
     * @param suspectCards
     * @param weaponCards
     * @return
     * @throws Exception 
     */
    public ArrayList<Card> displayRoomAction() throws Exception{
        CluedoGame cluedoGame = CluedoGame.getInstance();
        ArrayList<Card> handCards = cluedoGame.getCurrentPlayer().getHandCards();
        Map<Card,String> knownCards = cluedoGame.getCurrentPlayer().getKnownCards();
        String suspectCards = cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().suspectCardPath());
        String weaponCards = cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().weaponCardPath());
        ArrayList<Card> assumption = new ArrayList<>();
        printSeparator();
        System.out.println();
        System.out.println("You are in the " + cluedoGame.getCurrentPlayer().getPosition().getType() + ", make your assumption. \nPlease, note that the suspected room is the room you are into.");
        System.out.println();
        printSeparator();
        System.out.println();
        System.out.println("This is your hand ");
        System.out.println(handCards);
        System.out.println();
        System.out.println("These are your known cards ");
        System.out.println(knownCards);
        System.out.println();
        printSeparator();
        System.out.println();
        System.out.println("These are the suspect cards: ");
        System.out.println(suspectCards);
        System.out.println("These are the weapon cards: ");
        System.out.println(weaponCards);
        printSeparator();
        Boolean validInput = true;
        while (validInput) {
            System.out.println();
            System.out.print("Enter your suspected person:");
            String person = input.askString().trim();
            if (!GameController.checkIfSuspectCardExist(person)) {
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
            String weapon = input.askString().trim();
            if (!GameController.checkIfWeaponCardExist(weapon)) {
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

    //END_TURN

    public void displayDoActionResult(DoActionResult doActionResult) throws InterruptedException{
        if (doActionResult.isGameEnded()) {
            printWinner(CluedoGame.getInstance().getCurrentPlayer());
        } else {
            Cell cell = doActionResult.getCell();
            if (cell instanceof RoomCell) {
                ArrayList<Card> cardsShown = doActionResult.getCardsShown();
                if (cardsShown.isEmpty()) {
                    printNoCardsShown();
                } else {
                    printCardsShown(cardsShown);
                }
            }
            if (doActionResult.getEffect() != null) {
                if (doActionResult.getEffect() instanceof PeekCard) {
                    showPeekedCard(doActionResult);
                } else {
                    printEffect(doActionResult);
                }
            }
        }
    }

    /**
     * Print the winner of the game. This method is called when the game ends and a player has won. 
     * It takes a Player object as input and prints a congratulatory message with the player's name.
     * @param player
     * @throws InterruptedException 
     */
    public void printWinner(Player player) throws InterruptedException{
        printSeparator();
        System.out.println();
        LoadingBar.dramaticLoading(player.getUsername());
        VictoryScreen.show();
        System.out.println();
        printSeparator();
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
        printSeparator();
        System.out.println();
        System.out.println("You have discovered the following cards: ");
        for (Card card : cardsShown) {
            System.out.println("- " + card.getName());
        }
        System.out.println();
        printSeparator();
        System.out.println();
        System.out.println("In the next turns, try to find out if these cards are in the winning solution or if they are in someone's hand.");
        System.out.println();
        printSeparator();
    }

    /**
     * Print the effect of a chance card that was drawn by the player. 
     * This method takes a DoActionResult object as input, which contains information about the effect of the chance card.
     * @param doActionResult
     */
    public void printEffect(DoActionResult doActionResult){
        System.out.println("You have discovered the following effect: " + doActionResult.getEffect().getDescription());
        System.out.println();
        printSeparator();
    }

    /**
     * Show the card that was peeked at by the player as a result of their action in a room cell.
     * @param doActionResult
     */
    public void showPeekedCard(DoActionResult doActionResult){ 
        System.out.println("You peeked at the card: " + doActionResult.getCardsShown().get(0).getName() + " from " + CluedoGame.getInstance().getNextPlayer().getUsername() + "'s hand.");
        System.out.println();
        printSeparator();
    }

    public void displayEndTurn(DoActionResult doActionResult){
        if (doActionResult.isGameEnded()) {
            System.out.println("\nPress Enter to start a new game");
        } else {
            System.out.println("\nPress Enter to end your turn");
        }
        input.pressButton();
    }

}
