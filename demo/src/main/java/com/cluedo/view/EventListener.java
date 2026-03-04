package com.cluedo.view;

import com.cluedo.config.GameEvent;

/**
 * Functional interface for handling specific game events.
 * Allows defining specific behavior for each event type without using if/switch statements.
 */
@FunctionalInterface
public interface EventListener {
    /**
     * Handles a specific game event.
     * @param event The game event that triggered this listener
     * @throws Exception if an error occurs during event handling
     */
    void handle(GameEvent event) throws Exception;
}
