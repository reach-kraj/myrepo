package com.example.behaviouralpatterns.chainofresponsibility;

public class AuthHandler extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("AUTH")) {
            System.out.println("AuthHandler handled request.");
        } else if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler for request: " + request);
        }
    }
}