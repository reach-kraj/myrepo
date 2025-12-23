package com.example.saga.event;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class EventBus {
    private static final Map<String, List<Consumer<Object>>> listeners = new HashMap<>();

    public static void subscribe(String eventType, Consumer<Object> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public static void publish(String eventType, Object data) {
        if (listeners.containsKey(eventType)) {
            for (Consumer<Object> listener : listeners.get(eventType)) {
                listener.accept(data);
            }
        }
    }

    public static void clear() {
        listeners.clear();
    }
}
