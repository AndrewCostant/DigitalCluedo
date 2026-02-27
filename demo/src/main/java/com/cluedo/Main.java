package com.cluedo;


import com.cluedo.controller.GameController;
import com.cluedo.domain.Board;
import com.cluedo.domain.CluedoGame;
import com.cluedo.config.GameEvent;
import com.cluedo.view.InputView;
import com.cluedo.view.TurnView;
import com.cluedo.view.WelcomeView;

public class Main {
    public static void main(String[] args) throws Exception {
        
        CluedoGame cluedoGame = CluedoGame.getInstance();
        Board board = Board.getInstance();
        GameController controller = new GameController();
        InputView input = new InputView();
        WelcomeView welcomeView = new WelcomeView(controller);
        TurnView turnView = new TurnView(controller, input);

        cluedoGame.registerObserver(welcomeView, GameEvent.WELCOME);
        cluedoGame.registerObserver(welcomeView, GameEvent.SET_PLAYERS);
        cluedoGame.registerObserver(turnView, GameEvent.ROLL_DICES);
        cluedoGame.registerObserver(turnView, GameEvent.SELECT_DESTINATION);
        cluedoGame.registerObserver(turnView, GameEvent.DO_ACTION);
        board.registerObserver(turnView, GameEvent.END_TURN);

        cluedoGame.notifyObservers(GameEvent.WELCOME);
    }
}