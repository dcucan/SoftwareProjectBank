package bankband.bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EventBus {

    public interface Observer<T> {
        void observe(T event);
    }

    private static EventBus instance;

    private HashMap<Class, HashMap<String, Observer>> observers;

    private EventBus() {
        observers = new HashMap<>();
    }

    public <T> void subscribe(String id, Class<T> klass, Observer<T> observer) {
        if (observers.get(klass) == null) {
            observers.put(klass, new HashMap<>());
        }

        observers.get(klass).put(id, observer);
    }

    public void unsubscribe(String id, Class klass) {
        observers.get(klass).remove(id);
    }

    public void send(Object event) {
        Collection<Observer> list = observers.get(event.getClass()).values();

        if (list == null) {
            return;
        }

        for (Observer observer : list) {
            observer.observe(event);
        }
    }

    public static EventBus get() {
        if (instance == null) {
            instance = new EventBus();
        }

        return instance;
    }


}
