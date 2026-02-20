package controller;

import java.util.ArrayList;
import java.util.Map;

import domain.Board;
import domain.CluedoGame;
import domain.PeekCard;
import domain.RoomCell;
import domain.SpeedGameModeFactory;
import domain.SuspectC;
import domain.WeaponC;
import domain.Card;
import domain.Cell;
import domain.ClassicGameModeFactory;
import domain.dto.ActionResult;
import domain.dto.DoActionResult;
import domain.dto.RollResult;
import ui.TerminalUI;

public class GameController {
    
    public void Game(CluedoGame cluedoGame, TerminalUI ui) throws Exception {
        ui.printWelcome();
        int gameModeChoice = ui.askGameMode();
        switch (gameModeChoice) {
            case 1:
                cluedoGame.setGameMode(new ClassicGameModeFactory());
                break;
            case 2:
                cluedoGame.setGameMode(new SpeedGameModeFactory());
                break;   
            default:
                throw new IllegalArgumentException("Invalid game mode choice");
        }
        ArrayList<String> players = ui.initializePlayers();
        boolean gameOver = false;
        cluedoGame.setPlayers(players);
        cluedoGame.startGame();
        ui.initializeMap();
        System.out.println();
        ui.printBoardWithPlayers(cluedoGame.getPlayers());
    
        while (!gameOver) {
            // prima interazione: roll dice
            ui.startTurn(cluedoGame.getCurrentPlayer());
            RollResult rollResult = cluedoGame.rollDices();
            ui.printBoardWithPlayers(cluedoGame.getPlayers());
            ui.rollResult(rollResult);

            System.out.println("Soluzione " + cluedoGame.getWinningTriplet()); // debug

            int choice = -1;
            int choice2 = -1;
            // seconda interazione: scegliere dove muoversi
            switch (gameModeChoice) {
            case 1:
                ArrayList<Integer> destination = classicDestinationRoom(ui, rollResult);
                choice = destination.get(0);
                choice2 = destination.get(1);
                break;
            case 2:
                ui.displaySpeedDestination(rollResult);
                choice = rollResult.cells().iterator().next().getX();
                choice2 = rollResult.cells().iterator().next().getY();
                break;   
            default:
                throw new IllegalArgumentException("Invalid game mode choice");
            }

            ActionResult typeOfAction = cluedoGame.goToCell(Board.getInstance().getCellXY(choice, choice2));
            ui.printBoardWithPlayers(cluedoGame.getPlayers());

            String actionResultString = typeOfAction.getType();
            /*
             * Versione demo di gestione delle azioni della stanza :)(
             */
            // terza interazione: azione da eseguire in base alla cella in cui si è entrati
            SuspectC suspectedPerson = null;
            WeaponC suspectedWeapon = null;

            switch (actionResultString) {
                case "NORMAL_CELL":
                    ui.displayNormalCellAction();
                    break;
                case "CHANCE_CELL":
                    ui.displayChanceCellAction(typeOfAction.getCard());
                    break;
                case "GAMBLING_ROOM_CELL":
                    ui.displayGamblingRoomAction(typeOfAction);
                    break;
                default:
                    ArrayList<Card> handCards = cluedoGame.getCurrentPlayer().getHandCards();
                    Map<Card,String> knownCards = cluedoGame.getCurrentPlayer().getKnownCards();
                    String suspectCards = cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().suspectCardPath());
                    String weaponCards = cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().weaponCardPath());

                    System.out.println(cluedoGame.getWinningTriplet()); // debug

                    ArrayList<Card> assumption = ui.displayRoomAction(handCards, knownCards, suspectCards, weaponCards);
                    suspectedPerson = (SuspectC) assumption.get(0);
                    suspectedWeapon = (WeaponC) assumption.get(1);
            }

            DoActionResult result = Board.getInstance().doAction(suspectedPerson, suspectedWeapon);
            if (result.isGameEnded()) {
                gameOver = true;
                ui.printWinner(cluedoGame.getCurrentPlayer());
            } else {
                Cell cell = result.getCell();
                if (cell instanceof RoomCell) {
                    ArrayList<Card> cardsShown = result.getCardsShown();
                    if (cardsShown.isEmpty()) {
                        ui.printNoCardsShown();
                    } else {
                        ui.printCardsShown(cardsShown);
                    }
                }
                if (result.getEffect() != null) {
                    if (result.getEffect() instanceof PeekCard) {
                        ui.showPeekedCard(result);
                    } else {
                        ui.printEffect(result);
                    }
                }
            }
            ui.displayEndTurn();
            cluedoGame.endTurn();
        }
    }

    public ArrayList<Integer> classicDestinationRoom(TerminalUI ui, RollResult rollResult) {
        ArrayList<Integer> destination = ui.askDestination(rollResult);
        return destination;
    } 
}
