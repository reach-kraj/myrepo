package com.example.behaviouralpatterns.memento;


public class MementoMain {
    public static void main(String[] args) {
        Originator origin = new Originator();
        CareTaker care = new CareTaker();

        origin.set("State1");
        care.save(origin.saveToMemento());

        origin.set("State2");
        origin.restoreFromMemento(care.retrieve());
    }
}

