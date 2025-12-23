package com.example.structuralpatterns.proxy;

public class RealInternet implements Internet {
    @Override
    public void connectTo(String site) {
        System.out.println("Connecting to " + site);
    }
}
