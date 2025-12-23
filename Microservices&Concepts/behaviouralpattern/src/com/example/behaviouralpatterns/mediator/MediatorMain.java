package com.example.behaviouralpatterns.mediator;

public class MediatorMain {
    public static void main(String[] args) {
        User alice = new User("Alice");
        User bob = new User("Bob");

        alice.sendMessage("Hi Bob!");
        bob.sendMessage("Hey, Alice!");
    }
}
