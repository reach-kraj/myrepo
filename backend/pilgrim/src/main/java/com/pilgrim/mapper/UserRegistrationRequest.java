package com.pilgrim.mapper;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    // User fields
    private String userId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String username;
    private String email;
    private String phone;
    private String address;

    // Address fields (additional to User entity)
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
