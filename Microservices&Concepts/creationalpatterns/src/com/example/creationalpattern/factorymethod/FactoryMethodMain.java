package com.example.creationalpattern.factorymethod;

/**
 * Demo class with main() to show the Factory Method in action.
 */
public class FactoryMethodMain {
    public static void main(String[] args) {
        // Create an Email notification
        Notification emailNotif = NotificationFactory.createNotification("email");
        emailNotif.send();

        // Create an SMS notification
        Notification smsNotif = NotificationFactory.createNotification("sms");
        smsNotif.send();

        // Attempting to create an unknown type will throw an exception
        try {
            Notification unknown = NotificationFactory.createNotification("push");
            unknown.send();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
