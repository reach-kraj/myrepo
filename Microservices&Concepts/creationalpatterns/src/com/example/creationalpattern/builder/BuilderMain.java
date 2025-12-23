package com.example.creationalpattern.builder;

/**
 * Demo class to show the Builder Pattern in action.
 */
public class BuilderMain {
    public static void main(String[] args) {
        // Construct a User instance step-by-step
        User user = new User.Builder()
                        .firstName("Alice")
                        .lastName("Smith")
                        .age(30)
                        .email("alice.smith@example.com")
                        .phone("+1-555-1234")
                        .build();

        // Print out the constructed object
        System.out.println(user);
    }
}
