package com.example.creationalpattern.abstractfactory;

/**
 * Concrete Factory: creates light-themed products.
 */
public class LightFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new LightButton();
    }
}
