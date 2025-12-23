package com.example.creationalpattern.abstractfactory;

/**
 * Concrete Factory: creates dark-themed products.
 */
public class DarkFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new DarkButton();
    }
}
