package com.switchfully.eurder.api.dtos;

import com.switchfully.eurder.domain.users.Address;

import java.util.List;

public class ShowUserDto {
    private final long id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Address address;
    private String phoneNumber;
    private List<ShowOrderDto> allOrders;

    public ShowUserDto(long id, String firstName, String lastName, String eMail, Address address, String phoneNumber, List<ShowOrderDto> allOrders) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.allOrders = allOrders;
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

    public List<ShowOrderDto> getAllOrders() {
        return allOrders;
    }
}
