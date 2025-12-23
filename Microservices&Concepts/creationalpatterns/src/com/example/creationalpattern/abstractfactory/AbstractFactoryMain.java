package com.example.creationalpattern.abstractfactory;

/**
 * Demo class to show the Abstract Factory Pattern in action.
 */
public class AbstractFactoryMain {
    public static void main(String[] args) {
        // Use the Dark theme factory
        GUIFactory darkFactory = new DarkFactory();
        Button darkButton = darkFactory.createButton();
        darkButton.paint();  // Output: Rendering a dark button

        // Use the Light theme factory
        GUIFactory lightFactory = new LightFactory();
        Button lightButton = lightFactory.createButton();
        lightButton.paint();  // Output: Rendering a light button
    }
}
