package com.example.structuralpatterns.decorator;

public class DecoratorMain {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);

        System.out.println(coffee.getDescription()); // Simple Coffee, Milk, Sugar
        System.out.println("Total cost: $" + coffee.cost()); // 5 + 1.5 + 0.5 = 7.0
    }
}
