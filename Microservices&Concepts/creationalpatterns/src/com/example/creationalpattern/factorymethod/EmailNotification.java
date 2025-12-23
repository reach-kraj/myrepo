package com.example.creationalpattern.factorymethod;

/**
 * Concrete Product: implements the Notification interface for email.
 */
public class EmailNotification implements Notification {
    @Override
    public void send() {
        System.out.println("Sending Email Notification");
    }
}
