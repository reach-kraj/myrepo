package com.example.saga.log;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SagaLog {
    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());

    public void log(String message) {
        System.out.println(message); // Still log to console
        messages.add(message);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void clear() {
        messages.clear();
    }
}
