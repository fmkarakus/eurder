package com.switchfully.eurder.api.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateCustomerDto {
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String eMail;
    @NotBlank(message = "Street name cannot be blank")
    private String street;
    @NotBlank(message = "House number cannot be blank")
    private String houseNumber;
    @NotBlank(message = "Postcode cannot be blank")
    private String postCode;
    @NotBlank(message = "City cannot be blank")
    private String city;
    private String phoneNumber;

    public CreateCustomerDto(String firstName, String lastName, String eMail, String street, String houseNumber, String postCode, String city, String phoneNumber, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Size(min=8, message=" Password must be equal to or greater than 8 characters")
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
