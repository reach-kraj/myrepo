package com.example.behaviouralpatterns.visitor;

public class VisitorMain {
    public static void main(String[] args) {
        ItemElement[] items = {
            new Book(20, "1234"),
            new Book(100, "5678"),
            new Fruit(10, 2, "Banana"),
            new Fruit(5, 5, "Apple")
        };

        int total = 0;
        ShoppingCartVisitor visitor = new ShoppingCartVisitorImpl();
        for (ItemElement item : items) {
            total += item.accept(visitor);
        }

        System.out.println("Total cost: " + total);
    }
}
