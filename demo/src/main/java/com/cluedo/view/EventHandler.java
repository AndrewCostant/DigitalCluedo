package com.cluedo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cluedo.config.GameEvent;
import com.cluedo.events.GameObserver;

/**
 * Event handler that dispatches game events to registered listeners.
 * Eliminates the need for if/switch statements in the update method by allowing
 * direct registration of event-specific handlers.
 * 
 * Usage example:
 * <pre>
 * EventHandler handler = new EventHandler();
 * handler.on(GameEvent.ROLL_DICES, event -> startTurn());
 * handler.on(GameEvent.SELECT_DESTINATION, event -> selectDestination());
 * </pre>
 */
public class EventHandler implements GameObserver {
    
    private final Map<GameEvent, List<EventListener>> listeners;
    
    public EventHandler() {
        this.listeners = new HashMap<>();
    }
    
    /**
     * Registers a listener for a specific game event.
     * Multiple listeners can be registered for the same event.
     * 
     * @param event The game event to listen for
     * @param listener The listener that will handle the event
     */
    public void on(GameEvent event, EventListener listener) {
        listeners.computeIfAbsent(event, k -> new ArrayList<>()).add(listener);
    }
    
    /**
     * Called when a game event occurs. Dispatches the event to all registered listeners.
     * 
     * @param event The game event that occurred
     * @throws Exception if any listener throws an exception
     */
    @Override
    public void update(GameEvent event) throws Exception {
        List<EventListener> eventListeners = listeners.get(event);
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.handle(event);
            }
        }
    }
    
    /**
     * Removes all listeners for a specific event.
     * 
     * @param event The event to clear listeners for
     */
    public void clear(GameEvent event) {
        listeners.remove(event);
    }
    
    /**
     * Removes all registered listeners.
     */
    public void clearAll() {
        listeners.clear();
    }
}
