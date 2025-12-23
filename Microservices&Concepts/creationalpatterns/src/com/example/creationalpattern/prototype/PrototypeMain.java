package com.example.creationalpattern.prototype;

public class PrototypeMain {
    public static void main(String[] args) {
        // Create the original document
        Document original = new Document("This is the original content.");
        System.out.println("Original content: " + original.getContent());

        // Clone the original
        Document clone = original.clone();
        System.out.println("Cloned content:   " + clone.getContent());

        // Modify the clone
        clone.setContent("This is modified in the clone.");
        System.out.println("\nAfter modifying clone:");
        System.out.println("Original content: " + original.getContent());
        System.out.println("Cloned content:   " + clone.getContent());
    }
}
