package com.example.behaviouralpatterns.mediator;

public class User {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }

    public void sendMessage(String msg) {
        ChatRoom.showMessage(this, msg);
    }
}
