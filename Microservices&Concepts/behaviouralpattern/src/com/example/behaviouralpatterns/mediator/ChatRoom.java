package com.example.behaviouralpatterns.mediator;

public class ChatRoom {
    public static void showMessage(User user, String message) {
        System.out.println(user.getName() + " says: " + message);
    }
}
