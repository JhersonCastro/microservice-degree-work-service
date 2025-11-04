package co.edu.unicauca.degreework.Observer;

/**
 * Defines a listener interface for handling specific events.
 * Implementations react when an event of a given type occurs.
 */
public interface EventListener {

    /**
     * Called when an event is triggered.
     *
     * @param eventType the type of the event
     * @param data      additional data related to the event
     */
    void update(String eventType, Object data);
}