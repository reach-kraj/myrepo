package com.example.structuralpatterns.Composite;

import java.util.ArrayList;
import java.util.List;

public class Manager implements Employee {
    private String name;
    private List<Employee> team = new ArrayList<>();

    public Manager(String name) {
        this.name = name;
    }

    public void addEmployee(Employee e) {
        team.add(e);
    }

    public void removeEmployee(Employee e) {
        team.remove(e);
    }

    public void showDetails(String indent) {
        System.out.println(indent + "+ Manager: " + name);
        for (Employee e : team) {
            e.showDetails(indent + "  ");
        }
    }
}
