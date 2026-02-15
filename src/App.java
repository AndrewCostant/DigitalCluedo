import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import domain.*;
import ui.*;
import domain.dto.ActionResult;
import domain.dto.DoActionResult;
import domain.dto.RollResult;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println();
        TerminalUI.getInstance().printWelcome();
        System.out.println();

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

        boolean gameOver = false;
        CluedoGame.getInstance().setPlayers(players);
        CluedoGame.getInstance().startGame();
        System.out.println();
        TerminalUI.getInstance().printBoardWithPlayers(CluedoGame.getInstance().getPlayers());
        // loop dei turni
        while (!gameOver) {
            TerminalUI.getInstance().startTurn(CluedoGame.getInstance().getCurrentPlayer());
            RollResult rollResult = CluedoGame.getInstance().rollDices();
            TerminalUI.getInstance().rollResult(rollResult);
            boolean validChoice = false;
            int choice = -1;
            int choice2 = -1;

            System.out.println(CluedoGame.getInstance().getWinningTriplet()); // debug

            ArrayList<Integer> destination = TerminalUI.getInstance().askDestination(rollResult);
            choice = destination.get(0);
            choice2 = destination.get(1);

            ActionResult typeOfAction = CluedoGame.getInstance().goToCell(Board.getInstance().getCellXY(choice, choice2));
            TerminalUI.getInstance().printBoardWithPlayers(CluedoGame.getInstance().getPlayers());

            String actionResultString = typeOfAction.getType();


            /**
             * Versione demo di gestione delle azioni della stanza :)(
             */



            SuspectC suspectedPerson = null;
            WeaponC suspectedWeapon = null;

            switch (actionResultString) {
                case "NORMAL_CELL":
                    TerminalUI.getInstance().displayNormalCellAction();
                    break;
                case "CHANCE_CELL":
                    TerminalUI.getInstance().displayChanceCellAction(typeOfAction.getCard());
                    break;
                case "GAMBLING_ROOM_CELL":
                    TerminalUI.getInstance().displayGamblingRoomAction(typeOfAction);
                    break;
                default:
                    TerminalUI.getInstance().printBoardWithPlayers(CluedoGame.getInstance().getPlayers());
                    ArrayList<Card> handCards = CluedoGame.getInstance().getCurrentPlayer().getHandCards();
                    Map<Card,String> knownCards = CluedoGame.getInstance().getCurrentPlayer().getKnownCards();
                    String suspectCards = CluedoGame.getInstance().specificDeckByTypeToString("suspect");
                    String weaponCards = CluedoGame.getInstance().specificDeckByTypeToString("weapon");

                    System.out.println(CluedoGame.getInstance().getWinningTriplet()); // debug

                    TerminalUI.getInstance().displayRoomAction(handCards, knownCards, suspectCards, weaponCards);
            }

            DoActionResult result = Board.getInstance().doAction(suspectedPerson, suspectedWeapon);
            if (result.isGameEnded()) {
                gameOver = true;
                TerminalUI.getInstance().printWinner(CluedoGame.getInstance().getCurrentPlayer());
            } else {
                Cell cell = result.getCell();
                if (cell instanceof RoomCell) {
                    ArrayList<Card> cardsShown = result.getCardsShown();
                    if (cardsShown.isEmpty()) {
                        TerminalUI.getInstance().printNoCardsShown();
                    } else {
                        TerminalUI.getInstance().printCardsShown(cardsShown);
                    }
                }
                if (result.getEffect() != null) {
                    if (result.getEffect() instanceof PeekCard) {
                        TerminalUI.getInstance().showPeekedCard(result);
                    } else {
                        TerminalUI.getInstance().printEffect(result);
                    }
                }
                if (cell instanceof NormalCell) {
                    TerminalUI.getInstance().displayEndTurn();
                }
            }
            CluedoGame.getInstance().endTurn();
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

    
}
