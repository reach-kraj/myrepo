package com.example.behaviouralpatterns.memento;

public class CareTaker {
    private Memento memento;

    public void save(Memento m) {
        this.memento = m;
    }

    public Memento retrieve() {
        return memento;
    }
}