package com.cluedo.events;

import com.cluedo.config.GameEvent;

public interface GameObserver {
    void update(GameEvent event) throws Exception;
}
