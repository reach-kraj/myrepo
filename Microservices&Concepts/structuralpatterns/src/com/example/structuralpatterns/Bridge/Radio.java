package com.example.structuralpatterns.Bridge;
public class Radio implements Device {
    public void turnOn() { System.out.println("Radio is ON"); }
    public void turnOff() { System.out.println("Radio is OFF"); }
    public void setVolume(int percent) {
        System.out.println("Radio volume set to " + percent + "%");
    }
    public String getDeviceName() {
        return "Radio";
    }
}
