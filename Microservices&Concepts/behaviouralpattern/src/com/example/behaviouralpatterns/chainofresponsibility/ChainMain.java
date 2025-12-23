package com.example.behaviouralpatterns.chainofresponsibility;

public class ChainMain {
    public static void main(String[] args) {
        Handler auth = new AuthHandler();
        Handler log = new LogHandler();

        auth.setNext(log);

        auth.handleRequest("LOG");
        auth.handleRequest("AUTH");
        auth.handleRequest("DEBUG");
    }
}
