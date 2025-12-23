package com.example.creationalpattern.singleton;

public class SingletonMain {
    public static void main(String[] args) {
        // First call; instance is created
        DatabaseConnection conn1 = DatabaseConnection.getInstance();
        conn1.query("SELECT * FROM users");

        // Second call; same instance is returned
        DatabaseConnection conn2 = DatabaseConnection.getInstance();
        conn2.query("SELECT * FROM orders");

        // Verify both references point to the same object
        System.out.println("conn1 and conn2 are the same instance? " 
                           + (conn1 == conn2));
    }
}
