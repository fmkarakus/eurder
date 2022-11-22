package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.users.Address;
import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.domain.users.PostalCode;
import com.switchfully.eurder.domain.users.Role;
import org.springframework.stereotype.Repository;


import java.util.*;

@Repository
public class UserRepository {
    private final Map<Long, Person> userMap;

    public UserRepository() {
        this.userMap = new HashMap<>();
        addUsersFromRepository();
    }

    private void addUsersFromRepository() {
        Person admin = new Person("admin", "adminLastName", "admin@eurder.com", new Address("street", "12",new PostalCode( "1000", "Brussels")), "05555666554", "password");
        admin.setRole(Role.ADMIN);
        Person customer = new Person("customer", "customerLastName", "customer@test.be", new Address("street2", "22", new PostalCode("3500", "Hasselt")), "04445666554", "password");
        userMap.put(admin.getId(), admin);
        userMap.put(customer.getId(), customer);
    }


    public Optional<Person> getPersonbyEmail(String username) {
        return userMap.values().stream().filter(user -> user.geteEmail().equals(username)).findFirst();
    }

    public Person addCustomer(Person person) {
        userMap.put(person.getId(), person);
        return person;
    }

    public Collection<Person> getAllUsers() {
        return userMap.values();
    }

    public Person getUserById(String customerId) {
        return userMap.get(customerId);
    }

    public boolean eMailAlreadyExits(String email) {
        return userMap.values().stream().anyMatch(user -> user.geteEmail().equals(email));
    }
}
