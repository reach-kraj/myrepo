package com.example.structuralpatterns.Composite;

public class CompanyStructureMain {
    public static void main(String[] args) {
        // Leaf employees
        Employee dev1 = new Developer("Alice");
        Employee dev2 = new Developer("Bob");
        Employee designer = new Designer("Charlie");

        // Team Lead (Composite)
        Manager lead = new Manager("David");
        lead.addEmployee(dev1);
        lead.addEmployee(designer);

        // CEO (Composite)
        Manager ceo = new Manager("Eve");
        ceo.addEmployee(lead);
        ceo.addEmployee(dev2); // CEO manages Bob directly

        // Print full structure
        ceo.showDetails("");
    }
}
