package com.cluedo.view;

import com.cluedo.config.GameEvent;

public interface GameObserver {
    void update(GameEvent event) throws Exception;
}
