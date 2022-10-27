package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.domain.User;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Map<String, User> userMap;

    public UserRepository() {
        this.userMap = new HashMap<>();
        addUsersFromRepository();
    }

    private void addUsersFromRepository() {
        User admin = new User("admin", "adminLastName", "admin@eurder.com", new Address("street", "12", "1000", "Brussels"), "05555666554", "password");
        admin.setRole(Role.ADMIN);
        User customer = new User("customer", "customerLastName", "customer@test.be", new Address("street2", "22", "3500", "Hasselt"), "04445666554", "password");
        userMap.put(admin.getId(), admin);
        userMap.put(customer.getId(), customer);
    }


    public Optional<User> getPersonbyEmail(String username) {
        return userMap.values().stream().filter(user -> user.geteMail().equals(username)).findFirst();
    }
}
