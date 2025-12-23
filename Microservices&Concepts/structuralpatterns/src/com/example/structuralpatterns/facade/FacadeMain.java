package com.example.structuralpatterns.facade;

public class FacadeMain {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        computer.startComputer();
    }
}
