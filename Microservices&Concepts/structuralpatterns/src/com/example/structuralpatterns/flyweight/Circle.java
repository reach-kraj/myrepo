package com.example.structuralpatterns.flyweight;

public class Circle implements Shape {
    private final String type = "Circle";

    @Override
    public void draw(String color) {
        System.out.println("Drawing a " + color + " " + type);
    }
}
