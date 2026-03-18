package com.cluedo.controller;

import java.util.ArrayList;

import com.cluedo.domain.AbstractGameModeFactory;
import com.cluedo.domain.Board;
import com.cluedo.domain.CardFactory;
import com.cluedo.domain.Cell;
import com.cluedo.domain.CluedoGame;
import com.cluedo.domain.GameModeSimpleFactory;
import com.cluedo.domain.Player;
import com.cluedo.domain.SuspectC;
import com.cluedo.domain.Turn;
import com.cluedo.domain.WeaponC;
import com.cluedo.domain.dto.DoActionResult;
import com.cluedo.config.GameMode;


public class GameController {

    public static boolean checkIfSuspectCardExist(String name) {
        return CluedoGame.getInstance().checkIfASuspectCardExist(name);
    }

    public static boolean checkIfWeaponCardExist(String name) {
        return CluedoGame.getInstance().checkIfAWeaponCardExist(name);
    }

    public void setGameMode(GameMode string) {
        CluedoGame.getInstance().setGameMode(GameModeSimpleFactory.getFactory(string));
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

    public void doAction(ArrayList<String> assumption) {
        if (assumption.isEmpty()) {
            Board.getInstance().doAction(null, null);
        } 
        else {
            SuspectC suspectedPerson = (SuspectC) CardFactory.createCard("suspect", assumption.get(0));
            WeaponC suspectedWeapon = (WeaponC) CardFactory.createCard("weapon", assumption.get(1));
            Board.getInstance().doAction(suspectedPerson, suspectedWeapon);
        }
    }

    public void endTurn(DoActionResult doActionResult) {
        CluedoGame.getInstance().endTurn(doActionResult.isGameEnded());
    }

    public Player getCurrentPlayer() {
        return CluedoGame.getInstance().getCurrentPlayer();
    }

    public ArrayList<Player> getPlayers() {
        return CluedoGame.getInstance().getPlayers();
    }

    public Turn getCurrentTurn() {
        return CluedoGame.getInstance().getCurrentTurn();
    }

    public Player getNextPlayer() {
        return CluedoGame.getInstance().getNextPlayer();
    }

    public String getsuspectCardsToString() {
        CluedoGame cluedoGame = CluedoGame.getInstance();
        try {
            return cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().suspectCardPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getweaponCardsToString() {
        CluedoGame cluedoGame = CluedoGame.getInstance();
        try {
            return cluedoGame.specificDeckByTypeToString(cluedoGame.getGameModeFactory().weaponCardPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AbstractGameModeFactory getGameMode() {
        return CluedoGame.getInstance().getGameModeFactory();
    }

    public Cell getCellByCoordinates(int choice, int choice2) {
        return Board.getInstance().getCellXY(choice, choice2);
    }
}
