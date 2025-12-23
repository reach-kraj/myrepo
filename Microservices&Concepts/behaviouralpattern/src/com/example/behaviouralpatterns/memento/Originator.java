package com.example.behaviouralpatterns.memento;

public class Originator {
    private String state;

    public void set(String state) {
        System.out.println("Setting state to " + state);
        this.state = state;
    }

    public Memento saveToMemento() {
        System.out.println("Saving state to Memento");
        return new Memento(state);
    }

    public void restoreFromMemento(Memento m) {
        state = m.getState();
        System.out.println("State after restoring from Memento: " + state);
    }
}
