package com.example.behaviouralpatterns.observer;

public class YouTubeChannel implements Observer {
    private final String channel;

    public YouTubeChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void update(String msg) {
        System.out.println("Channel " + channel + " update: " + msg);
    }
}
