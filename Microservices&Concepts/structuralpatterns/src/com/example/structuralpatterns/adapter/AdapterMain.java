package com.example.structuralpatterns.adapter;

/**
 * Demo class to show the Adapter Pattern in action.
 */
public class AdapterMain {
    public static void main(String[] args) {
        // Original Lightning device
        LightningPort iphone = new iPhone();

        // Adapter wraps the LightningPort so it can be used as USBPort
        USBPort usbAdapter = new LightningToUSBAdapter(iphone);

        // Client uses the USBPort interface
        usbAdapter.connectUSB();  // Internally calls connectLightning()
    }
}
