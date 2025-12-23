package com.example.structuralpatterns.flyweight;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory {
    private static final Map<String, Shape> circleMap = new HashMap<>();

    public static Shape getCircle() {
        return circleMap.computeIfAbsent("circle", k -> new Circle());
    }
}
