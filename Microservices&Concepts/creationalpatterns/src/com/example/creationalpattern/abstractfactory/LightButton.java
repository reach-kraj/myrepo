package com.example.creationalpattern.abstractfactory;

/**
 * Concrete Product: Light-themed button.
 */
public class LightButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a light button");
    }
}
