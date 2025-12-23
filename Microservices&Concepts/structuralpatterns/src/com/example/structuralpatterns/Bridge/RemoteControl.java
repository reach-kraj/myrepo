package com.example.structuralpatterns.Bridge;
public abstract class RemoteControl {
    protected Device device;

    public RemoteControl(Device device) {
        this.device = device;
    }

    public abstract void togglePower();
    public abstract void volumeUp();

    public void showDeviceInfo() {
        System.out.println("Currently controlling: " + device.getDeviceName());
    }
}
