package com.example.structuralpatterns.flyweight;

public class FlyweightMain {
    public static void main(String[] args) {
        for (String color : new String[]{"Red", "Green", "Blue"}) {
            Shape circle = ShapeFactory.getCircle();
            circle.draw(color);
        }
    }
}
