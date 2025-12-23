package com.example.creationalpattern.abstractfactory;

/**
 * Concrete Product: Dark-themed button.
 */
public class DarkButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a dark button");
    }
}
