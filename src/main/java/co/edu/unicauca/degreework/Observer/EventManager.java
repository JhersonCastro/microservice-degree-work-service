package co.edu.unicauca.degreework.Observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventManager {

    private final Map<String, List<EventListener>> listeners = new HashMap<>();

    // Suscribir un listener a un tipo de evento específico
    public void subscribe(String eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    // Desuscribir un listener de un tipo de evento
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    // Notificar a todos los listeners suscritos a un tipo de evento
    public void notify(String eventType, Object data) {
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            for (EventListener listener : users) {
                listener.update(eventType, data);
            }
        }
    }
}