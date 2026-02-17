import java.util.ArrayList;
import java.util.Map;


import domain.*;
import ui.*;
import domain.dto.ActionResult;
import domain.dto.DoActionResult;
import domain.dto.RollResult;

public class App {

    public static void main(String[] args) throws Exception {
        TerminalUI.getInstance().printWelcome();
        
        ArrayList<String> players = TerminalUI.getInstance().initializePlayers();
        boolean gameOver = false;
        CluedoGame.getInstance().setPlayers(players);
        CluedoGame.getInstance().startGame();
        System.out.println();
        TerminalUI.getInstance().printBoardWithPlayers(CluedoGame.getInstance().getPlayers());
    
        while (!gameOver) {
            TerminalUI.getInstance().startTurn(CluedoGame.getInstance().getCurrentPlayer());
            RollResult rollResult = CluedoGame.getInstance().rollDices();
            TerminalUI.getInstance().rollResult(rollResult);
            int choice = -1;
            int choice2 = -1;

            System.out.println("Soluzione " + CluedoGame.getInstance().getWinningTriplet()); // debug

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
                    ArrayList<Card> handCards = CluedoGame.getInstance().getCurrentPlayer().getHandCards();
                    Map<Card,String> knownCards = CluedoGame.getInstance().getCurrentPlayer().getKnownCards();
                    String suspectCards = CluedoGame.getInstance().specificDeckByTypeToString("suspect");
                    String weaponCards = CluedoGame.getInstance().specificDeckByTypeToString("weapon");

                    System.out.println(CluedoGame.getInstance().getWinningTriplet()); // debug

                    ArrayList<Card> assumption = TerminalUI.getInstance().displayRoomAction(handCards, knownCards, suspectCards, weaponCards);
                    suspectedPerson = (SuspectC) assumption.get(0);
                    suspectedWeapon = (WeaponC) assumption.get(1);
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
