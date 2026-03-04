package com.cluedo;


import com.cluedo.config.EventDispatcher;
import com.cluedo.config.GameEvent;
import com.cluedo.controller.GameController;
import com.cluedo.domain.Board;
import com.cluedo.domain.CluedoGame;
import com.cluedo.view.InputView;
import com.cluedo.view.TurnView;
import com.cluedo.view.WelcomeView;

public class Main {
    public static void main(String[] args) throws Exception {
        
        CluedoGame.getInstance();
        Board.getInstance();
        GameController controller = new GameController();
        InputView input = new InputView();
        WelcomeView welcomeView = new WelcomeView(controller);
        TurnView turnView = new TurnView(controller, input);

        // Register event handlers via EventDispatcher
        EventDispatcher dispatcher = EventDispatcher.getInstance();
        dispatcher.register(GameEvent.WELCOME, welcomeView.getEventHandler());
        dispatcher.register(GameEvent.SET_PLAYERS, welcomeView.getEventHandler());
        dispatcher.register(GameEvent.ROLL_DICES, turnView.getEventHandler());
        dispatcher.register(GameEvent.SELECT_DESTINATION, turnView.getEventHandler());
        dispatcher.register(GameEvent.DO_ACTION, turnView.getEventHandler());
        dispatcher.register(GameEvent.END_TURN, turnView.getEventHandler());

        dispatcher.dispatch(GameEvent.WELCOME);
    }
}