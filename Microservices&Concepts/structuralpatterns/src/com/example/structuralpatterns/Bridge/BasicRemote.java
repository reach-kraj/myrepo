package com.example.structuralpatterns.Bridge;
public class BasicRemote extends RemoteControl {
    private boolean isOn = false;
    private int volume = 50;

    public BasicRemote(Device device) {
        super(device);
    }

    public void togglePower() {
        if (isOn) {
            device.turnOff();
            isOn = false;
        } else {
            device.turnOn();
            isOn = true;
        }
    }

    public void volumeUp() {
        volume += 10;
        if (volume > 100) volume = 100;
        device.setVolume(volume);
    }
}
