package com.example.structuralpatterns.proxy;

public class ProxyMain {
    public static void main(String[] args) {
        Internet internet = new InternetProxy();
        internet.connectTo("google.com");
        internet.connectTo("facebook.com");
    }
}
