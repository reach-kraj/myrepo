package com.example.behaviouralpatterns.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private final List<Observer> observers = new ArrayList<>();
    private String message;

    public void attach(Observer obs) {
        observers.add(obs);
    }

    public void notifyAllObservers() {
        for (Observer obs : observers) {
            obs.update(message);
        }
    }

    public void setMessage(String msg) {
        System.out.println("Message sent: " + msg);
        this.message = msg;
        notifyAllObservers();
    }
}
