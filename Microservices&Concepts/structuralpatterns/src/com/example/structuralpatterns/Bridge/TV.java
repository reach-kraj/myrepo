package com.example.structuralpatterns.Bridge;
public class TV implements Device {
    public void turnOn() { System.out.println("TV is ON"); }
    public void turnOff() { System.out.println("TV is OFF"); }
    public void setVolume(int percent) {
        System.out.println("TV volume set to " + percent + "%");
    }
    public String getDeviceName() {
        return "TV";
    }
}
