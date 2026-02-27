package com.cluedo.controller;

import java.util.ArrayList;


import com.cluedo.config.GameModeRegistry;
import com.cluedo.domain.Board;
import com.cluedo.domain.Card;
import com.cluedo.domain.Cell;
import com.cluedo.domain.CluedoGame;
import com.cluedo.domain.SuspectC;
import com.cluedo.domain.WeaponC;
import com.cluedo.domain.dto.DoActionResult;


public class GameController {
    
    /* public void Game(CluedoGame cluedoGame, TerminalUI ui) throws Exception {
        
        // presentation and game mode selection
        ui.printWelcome();
        AbstractGameModeFactory gameMode = ui.askGameMode();
        cluedoGame.setGameMode(gameMode);
        String gameModeChoice = gameMode.getGameModeName();
        
        // initialization of players and game
        ArrayList<String> players = ui.initializePlayers();
        boolean gameOver = false;
        cluedoGame.setPlayers(players);
        cluedoGame.startGame();
        ui.initializeMap();
        ui.printBoardWithPlayers(cluedoGame.getPlayers());
        
        // main game loop
        while (!gameOver) {
            
            // first interaction: roll the dice
            ui.startTurn(cluedoGame.getCurrentPlayer());
            RollResult rollResult = cluedoGame.rollDices();
            ui.printBoardWithPlayers(cluedoGame.getPlayers());
            ui.rollResult(rollResult);

            // System.out.println("Soluzione " + cluedoGame.getWinningTriplet()); // debug

            // second interaction: choose destination cell based on the roll result and game mode
            int choice = -1;    
            int choice2 = -1;
            switch (gameModeChoice) {
            case "Classic":
                ArrayList<Integer> destination = ui.askDestination(rollResult);
                choice = destination.get(0);
                choice2 = destination.get(1);
                break;
            case "Speed":
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

            // third interaction: do a specific action based on the type of cell the player has moved to
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
                default:
                    ArrayList<Card> handCards = cluedoGame.getCurrentPlayer().getHandCards();
                    Map<Card,String> knownCards = cluedoGame.getCurrentPlayer().getKnownCards();
                    String suspectCards = cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().suspectCardPath());
                    String weaponCards = cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().weaponCardPath());

                    //System.out.println(cluedoGame.getWinningTriplet()); // debug

                    ArrayList<Card> assumption = ui.displayRoomAction(handCards, knownCards, suspectCards, weaponCards);
                    suspectedPerson = (SuspectC) assumption.get(0);
                    suspectedWeapon = (WeaponC) assumption.get(1);
            }

            DoActionResult result = Board.getInstance().doAction(suspectedPerson, suspectedWeapon);
            // end turn logic
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
    } */

    public static boolean checkIfExist(String name) {
        return CluedoGame.getInstance().checkIfACardExist(name);
    }

    public void setGameMode(String string) {
        CluedoGame.getInstance().setGameMode(GameModeRegistry.getMode(string));
    }

    public void setPlayers(ArrayList<String> players) {
        CluedoGame.getInstance().setPlayers(players);
        CluedoGame.getInstance().startGame();
    }

    public void rollDices() {
        CluedoGame.getInstance().rollDices();
    }

    public void goToCell(Cell destination) {
        CluedoGame.getInstance().goToCell(destination);
    }

    public void doAction(ArrayList<Card> assumption) {
        if (assumption.isEmpty()) {
            Board.getInstance().doAction(null, null);
        } 
        else {
            SuspectC suspectedPerson = (SuspectC) assumption.get(0);
            WeaponC suspectedWeapon = (WeaponC) assumption.get(1);
            Board.getInstance().doAction(suspectedPerson, suspectedWeapon);
        }
    }

    public void endTurn(DoActionResult doActionResult) {
        CluedoGame.getInstance().endTurn(doActionResult.isGameEnded());
    }
}
