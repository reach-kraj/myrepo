package com.example.structuralpatterns.Composite;

public class Designer implements Employee {
    private String name;
    private String role;

    public Designer(String name) {
        this.name = name;
        this.role = "Designer";
    }

    public void showDetails(String indent) {
        System.out.println(indent + "- " + role + ": " + name);
    }
}
