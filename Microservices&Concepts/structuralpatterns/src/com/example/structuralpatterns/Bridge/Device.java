package com.example.structuralpatterns.Bridge;
public interface Device {
    void turnOn();
    void turnOff();
    void setVolume(int percent);
    String getDeviceName();  // For identification
}
