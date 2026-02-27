package com.cluedo.domain;

import java.util.Set;

import com.cluedo.config.GameEvent;
import com.cluedo.view.GameObserver;

public interface Subject {
    void registerObserver(GameObserver observer, GameEvent event);
    void registerObserver(GameObserver observer, Set<GameEvent> events);
    void removeObserver(GameObserver observer);
    void notifyObservers(GameEvent event);
}
