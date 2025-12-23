package com.example.behaviouralpatterns.observer;

public class ObserverMain {
    public static void main(String[] args) {
        Subject subject = new Subject();

        subject.attach(new EmailSubscriber("Alice"));
        subject.attach(new YouTubeChannel("TechReview"));

        subject.setMessage("New video released!");
    }
}
