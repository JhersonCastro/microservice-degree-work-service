package co.edu.unicauca.degreework.Observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages event listeners and dispatches events to subscribed listeners.
 * Provides methods to subscribe, unsubscribe, and notify event listeners.
 */
@Component
public class EventManager {

    private final Map<String, List<EventListener>> listeners = new HashMap<>();

    /**
     * Subscribes a listener to a specific event type.
     *
     * @param eventType the type of event to listen for
     * @param listener  the listener to subscribe
     */
    public void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    /**
     * Unsubscribes a listener from a specific event type.
     *
     * @param eventType the type of event to unsubscribe from
     * @param listener  the listener to remove
     */
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    /**
     * Notifies all subscribed listeners of the specified event type.
     *
     * @param eventType the type of event that occurred
     * @param data      additional data associated with the event
     */
    public void notify(String eventType, Object data) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            for (EventListener listener : users) {
                listener.update(eventType, data);
            }
        }
    }
}
