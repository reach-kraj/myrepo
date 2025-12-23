package com.example.creationalpattern.factorymethod;

/**
 * Concrete Product: implements the Notification interface for SMS.
 */
public class SMSNotification implements Notification {
    @Override
    public void send() {
        System.out.println("Sending SMS Notification");
    }
}
