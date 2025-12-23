package com.example.creationalpattern.factorymethod;

/**
 * The Creator class declares the factory method that returns new Notification instances.
 */
public class NotificationFactory {

    /**
     * Factory Method: returns a Notification object based on the given type.
     *
     * @param channel "email" or "sms"
     * @return instance of EmailNotification or SMSNotification
     */
    public static Notification createNotification(String channel) {
        return switch (channel.toLowerCase()) {
            case "email" -> new EmailNotification();
            case "sms"   -> new SMSNotification();
            default      -> throw new IllegalArgumentException("Unknown channel: " + channel);
        };
    }
}
