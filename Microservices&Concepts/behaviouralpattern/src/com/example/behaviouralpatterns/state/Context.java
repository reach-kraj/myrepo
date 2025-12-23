package com.example.behaviouralpatterns.state;

public class Context {
    private State state;

    public Context() {
        state = null;
    }

    public void setState(State s) {
        this.state = s;
    }

    public State getState() {
        return state;
    }
}