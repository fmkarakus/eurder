package com.switchfully.eurder.api.dtos;


public class CreateCustomerDto {
    private String firstName;
    private String lastName;
    private String eMail;
    private String street;
    private String houseNumber;
    private String postCode;
    private String city;
    private String phoneNumber;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
