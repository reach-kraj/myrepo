package com.example.structuralpatterns.adapter;

/**
 * Adapter: makes a LightningPort look like a USBPort.
 */
public class LightningToUSBAdapter implements USBPort {
    private final LightningPort lightningDevice;

    public LightningToUSBAdapter(LightningPort device) {
        this.lightningDevice = device;
    }

    @Override
    public void connectUSB() {
        // Delegate to the LightningPort
        lightningDevice.connectLightning();
    }
}
