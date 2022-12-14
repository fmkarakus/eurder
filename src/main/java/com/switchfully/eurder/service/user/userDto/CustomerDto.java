package com.switchfully.eurder.service.user.userDto;

import com.switchfully.eurder.domain.users.Address;
import com.switchfully.eurder.domain.users.Role;

public class CustomerDto {
    private long id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Address address;
    private String phoneNumber;
    private Role role;

    public CustomerDto(long id, String firstName, String lastName, String eMail, Address address, String phoneNumber, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public long getId() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }
}
