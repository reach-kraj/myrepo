package com.example.behaviouralpatterns.visitor;

public class Book implements ItemElement {
    private final int price;
    private final String isbn;

    public Book(int price, String isbn) {
        this.price = price;
        this.isbn = isbn;
    }

    public int getPrice() {
        return price;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public int accept(ShoppingCartVisitor visitor) {
        return visitor.visit(this);
    }
}
