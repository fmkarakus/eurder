package com.switchfully.eurder.domain.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "postal_code")
public class PostalCode {
    @Id
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "city")
    private String city;

    public PostalCode() {
    }

    public PostalCode(String postcode, String city) {
        this.postcode = postcode;
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

}
