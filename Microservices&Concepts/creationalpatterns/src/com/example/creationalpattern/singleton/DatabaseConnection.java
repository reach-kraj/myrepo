package com.example.creationalpattern.singleton;

/**
 * Thread-safe Singleton for simulating a database connection.
 */
public class DatabaseConnection {

    // 1) Private static instance variable
    private static DatabaseConnection instance;

    // 2) Private constructor prevents external instantiation
    private DatabaseConnection() {
        System.out.println("Connecting to the database...");
    }

    // 3) Public method to provide access to the instance
    //    synchronized to ensure only one thread can execute at a time
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Example operation on the singleton
    public void query(String sql) {
        System.out.println("Executing SQL query: " + sql);
    }
}
