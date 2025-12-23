package com.example.structuralpatterns.Composite;

public class Developer implements Employee {
    private String name;
    private String role;

    public Developer(String name) {
        this.name = name;
        this.role = "Developer";
    }

    public void showDetails(String indent) {
        System.out.println(indent + "- " + role + ": " + name);
    }
}
