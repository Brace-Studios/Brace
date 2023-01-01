package dev.dubhe.brace.events;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
    public final Map<Class<? extends Event>, List<EventHandler<? extends Event>>> eventMap = new ConcurrentHashMap<>();

    public <T extends Event> void release(Class<T> type, T t, EventActuator actuator) {
        for (Map.Entry<Class<? extends Event>, List<EventHandler<? extends Event>>> entry : eventMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                for (EventHandler<?> handler : entry.getValue()) {
                    ((EventHandler<T>) handler).handler(t);
                }
            }
        }
        if (t instanceof Cancellable tc) {
            if (tc.isCanceled()) return;
        }
        actuator.actuator();
    }

    public <T extends Event> void release(Class<T> type, T t) {
        for (Map.Entry<Class<? extends Event>, List<EventHandler<? extends Event>>> entry : eventMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                for (EventHandler<?> handler : entry.getValue()) {
                    ((EventHandler<T>) handler).handler(t);
                }
            }
        }
    }

    public <T extends Event> void listen(Class<T> type, EventHandler<T> handler) {
        if (eventMap.containsKey(type)) {
            eventMap.get(type).add(handler);
        } else {
            List<EventHandler<? extends Event>> handlerList = new Vector<>();
            handlerList.add(handler);
            eventMap.put(type, handlerList);
        }
    }

    public interface EventHandler<T extends Event> {
        void handler(T event);
    }

    public interface EventActuator {
        void actuator();
    }
}
