package com.example.manualcircuitbreakerdemo;

import java.util.function.Supplier;

public class CircuitBreaker {
    public enum State {
        CLOSED, OPEN, HALF_OPEN
    }

    private State state = State.CLOSED;
    private final long waitDurationInOpenState; // Wait time in OPEN state (milliseconds)
    private long lastFailureTime;

    public CircuitBreaker(long waitDurationInOpenState) {
        this.waitDurationInOpenState = waitDurationInOpenState;
        this.lastFailureTime = 0;
    }

    public <T> T execute(Supplier<T> serviceCall, T fallbackResult) {
        transition();

        if (state == State.OPEN) {
            return fallbackResult;
        }

        try {
            T result = serviceCall.get();
            if (state == State.HALF_OPEN) {
                state = State.CLOSED;
                System.out.println("Circuit Breaker transitioned to CLOSED due to successful call");
            }
            return result;
        } catch (Exception e) {
            lastFailureTime = System.currentTimeMillis();
            if (state != State.OPEN) {
                state = State.OPEN;
                System.out.println("Circuit Breaker transitioned to OPEN due to failure");
            }
            return fallbackResult;
        }
    }

    private void transition() {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime >= waitDurationInOpenState) {
                state = State.HALF_OPEN;
                System.out.println("Circuit Breaker transitioned to HALF_OPEN after wait period");
            }
        }
    }

    public State getState() {
        return state;
    }
}