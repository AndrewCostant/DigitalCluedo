package com.cluedo.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cluedo.view.GameObserver;

/**
 * Centralized event dispatcher that manages all event registration and dispatching.
 * This class removes the responsibility from CluedoGame and Board to manage observers,
 * providing a single point of control for the entire event system.
 */
public class EventDispatcher {
    
    private static volatile EventDispatcher instance;
    private final Map<GameEvent, Set<GameObserver>> observers;
    
    private EventDispatcher() {
        this.observers = new HashMap<>();
    }
    
    /**
     * Gets the singleton instance of the EventDispatcher.
     * @return the EventDispatcher instance
     */
    public static EventDispatcher getInstance() {
        if (instance == null) {
            synchronized (EventDispatcher.class) {
                if (instance == null) {
                    instance = new EventDispatcher();
                }
            }
        }
        return instance;
    }
    
    /**
     * Registers an observer for a specific game event.
     * 
     * @param event The game event to listen for
     * @param observer The observer that will handle the event
     */
    public void register(GameEvent event, GameObserver observer) {
        observers.computeIfAbsent(event, k -> new HashSet<>()).add(observer);
    }
    
    /**
     * Registers an observer for multiple game events.
     * 
     * @param events The set of game events to listen for
     * @param observer The observer that will handle the events
     */
    public void register(Set<GameEvent> events, GameObserver observer) {
        for (GameEvent event : events) {
            register(event, observer);
        }
    }
    
    
    /**
     * Removes an observer from a specific event.
     * 
     * @param event The event to remove the observer from
     * @param observer The observer to remove
     */
    public void unregister(GameEvent event, GameObserver observer) {
        Set<GameObserver> eventObservers = observers.get(event);
        if (eventObservers != null) {
            eventObservers.remove(observer);
            if (eventObservers.isEmpty()) {
                observers.remove(event);
            }
        }
    }
    
    /**
     * Removes an observer from all events.
     * 
     * @param observer The observer to remove
     */
    public void unregister(GameObserver observer) {
        List<GameEvent> eventsToClean = new ArrayList<>();
        for (Map.Entry<GameEvent, Set<GameObserver>> entry : observers.entrySet()) {
            entry.getValue().remove(observer);
            if (entry.getValue().isEmpty()) {
                eventsToClean.add(entry.getKey());
            }
        }
        // Clean up empty event entries
        for (GameEvent event : eventsToClean) {
            observers.remove(event);
        }
    }
    
    /**
     * Dispatches an event to all registered observers.
     * This method notifies all observers registered for the specified event.
     * 
     * @param event The game event to dispatch
     * @throws RuntimeException if any observer throws an exception during notification
     */
    public void dispatch(GameEvent event) {
        Set<GameObserver> eventObservers = observers.get(event);
        if (eventObservers != null) {
            // Create a copy to avoid ConcurrentModificationException
            Set<GameObserver> observersCopy = new HashSet<>(eventObservers);
            for (GameObserver observer : observersCopy) {
                try {
                    observer.update(event);
                } catch (Exception e) {
                    throw new RuntimeException("Error dispatching event " + event + " to observer", e);
                }
            }
        }
    }
    
    /**
     * Clears all observers for a specific event.
     * 
     * @param event The event to clear observers for
     */
    public void clear(GameEvent event) {
        observers.remove(event);
    }
    
    /**
     * Clears all registered observers for all events.
     */
    public void clearAll() {
        observers.clear();
    }
    
    /**
     * Returns the number of observers registered for a specific event.
     * 
     * @param event The event to count observers for
     * @return The number of observers registered for the event
     */
    public int getObserverCount(GameEvent event) {
        Set<GameObserver> eventObservers = observers.get(event);
        return eventObservers != null ? eventObservers.size() : 0;
    }
    
    /**
     * Checks if there are any observers registered for a specific event.
     * 
     * @param event The event to check
     * @return true if there are observers registered for the event, false otherwise
     */
    public boolean hasObservers(GameEvent event) {
        return getObserverCount(event) > 0;
    }
}
