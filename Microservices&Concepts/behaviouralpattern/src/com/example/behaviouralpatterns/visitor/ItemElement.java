package com.example.behaviouralpatterns.visitor;

public interface ItemElement {
    int accept(ShoppingCartVisitor visitor);
}