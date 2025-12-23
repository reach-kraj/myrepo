package com.example.behaviouralpatterns.strategy;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    static class Item {
        String name;
        int price;
        Item(String n, int p) { name = n; price = p; }
    }

    private final List<Item> items = new ArrayList<>();

    public void addItem(String name, int price) {
        items.add(new Item(name, price));
    }

    public int calculateTotal() {
        return items.stream().mapToInt(i -> i.price).sum();
    }

    public void pay(PaymentStrategy strategy) {
        int amount = calculateTotal();
        strategy.pay(amount);
    }
}