package com.switchfully.eurder.domain.users;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Address {
    @Column(name = "street_name")
    private String street;
    @Column(name = "house_number")
    private String houseNumber;
    @ManyToOne
    @JoinColumn(name = "postcode")
    private PostalCode postCode;

    public Address(String street, String houseNumber, PostalCode postCode) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
    }

    public Address() {

    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public PostalCode getPostCode() {
        return postCode;
    }

}
