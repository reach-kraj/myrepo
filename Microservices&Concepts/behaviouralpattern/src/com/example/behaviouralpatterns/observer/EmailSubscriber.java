package com.example.behaviouralpatterns.observer;

public class EmailSubscriber implements Observer {
    private final String name;

    public EmailSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String msg) {
        System.out.println(name + " received email: " + msg);
    }
}