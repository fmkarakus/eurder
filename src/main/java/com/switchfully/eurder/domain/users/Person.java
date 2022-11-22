package com.switchfully.eurder.domain.users;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name="person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    @Column(name="id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
    @Embedded
    private Address address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "password")
    private String password;

    public Person(String firstName, String lastName, String email, Address adress, String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = adress;
        this.role = Role.CUSTOMER;
    }

    public Person() {

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

    public String geteEmail() {
        return email;
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
