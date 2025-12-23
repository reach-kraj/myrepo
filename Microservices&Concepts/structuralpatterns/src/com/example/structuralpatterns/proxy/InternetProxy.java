package com.example.structuralpatterns.proxy;

import java.util.Arrays;
import java.util.List;

public class InternetProxy implements Internet {
    private final Internet realInternet = new RealInternet();
    private static final List<String> blockedSites = Arrays.asList("facebook.com", "youtube.com");

    @Override
    public void connectTo(String site) {
        if (blockedSites.contains(site.toLowerCase())) {
            System.out.println("Access Denied to " + site);
        } else {
            realInternet.connectTo(site);
        }
    }
}
