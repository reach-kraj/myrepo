package com.example.behaviouralpatterns.strategy;

public class StrategyMain {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Book", 120);
        cart.addItem("Pen", 20);

        cart.pay(new CreditCardStrategy("1234567890123456"));
        cart.pay(new PayPalStrategy("user@example.com"));
    }
}