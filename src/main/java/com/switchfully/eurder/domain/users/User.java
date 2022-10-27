package com.switchfully.eurder.domain.users;

import java.util.UUID;

public class User {
    private final String id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Address address;
    private String phoneNumber;
    private Role role;
    private String password;

    public User(String firstName, String lastName, String eMail, Address adress, String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.address = adress;
        this.role = Role.CUSTOMER;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean doesPasswordMatch(String password) {
        return password.equals(this.password);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean canHaveAccessTo(Feature feature) {
        return role.containsFeature(feature);
    }

    public String getFullName() {
        return firstName+" "+lastName;
    }
}
