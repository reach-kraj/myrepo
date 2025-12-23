package com.example.behaviouralpatterns.chainofresponsibility;

public class LogHandler extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("LOG")) {
            System.out.println("LogHandler handled request.");
        } else if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler for request: " + request);
        }
    }
}
