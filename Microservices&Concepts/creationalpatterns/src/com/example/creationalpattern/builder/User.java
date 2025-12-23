package com.example.creationalpattern.builder;

/**
 * Immutable User class constructed via the Builder.
 */
public class User {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String email;
    private final String phone;

    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName  = builder.lastName;
        this.age       = builder.age;
        this.email     = builder.email;
        this.phone     = builder.phone;
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public int    getAge()       { return age; }
    public String getEmail()     { return email; }
    public String getPhone()     { return phone; }

    @Override
    public String toString() {
        return "User{" +
               "firstName='" + firstName + '\'' +
               ", lastName='"  + lastName  + '\'' +
               ", age="        + age       +
               ", email='"     + email     + '\'' +
               ", phone='"     + phone     + '\'' +
               '}';
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private int    age;
        private String email;
        private String phone;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }
        public User build() {
            // Optional: add validation here
            return new User(this);
        }
    }
}
