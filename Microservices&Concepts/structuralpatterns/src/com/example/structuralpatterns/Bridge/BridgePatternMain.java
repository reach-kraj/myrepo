package com.example.structuralpatterns.Bridge;
public class BridgePatternMain {
    public static void main(String[] args) {
        Device tv = new TV();
        RemoteControl remote1 = new BasicRemote(tv);

        remote1.showDeviceInfo();  // Shows "Currently controlling: TV"
        remote1.togglePower();     // Turns TV on
        remote1.volumeUp();        // Sets volume

        System.out.println("-----");

        Device radio = new Radio();
        RemoteControl remote2 = new BasicRemote(radio);

        remote2.showDeviceInfo();  // Shows "Currently controlling: Radio"
        remote2.togglePower();     // Turns Radio on
        remote2.volumeUp();        // Sets volume
    }
}
