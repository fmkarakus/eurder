package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.domain.users.Person;
import com.switchfully.eurder.repositories.PersonRepository;
import com.switchfully.eurder.domain.users.UsernamePassword;
import com.switchfully.eurder.service.exceptions.UnauthorizatedException;
import com.switchfully.eurder.service.exceptions.WrongCredentialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private final PersonRepository personRepository;

    public SecurityService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public void validateAuthorization(String authorization, Feature feature) {
        UsernamePassword usernamePassword = getUsernamePassword(authorization);

        Person person = personRepository.findByEmail(usernamePassword.getUsername()).orElseGet(() -> {
            logger.error("Wrong credentials");
            throw new WrongCredentialException();
        });

        if (!person.doesPasswordMatch(usernamePassword.getPassword())) {
            logger.error("Password does not match for user " + usernamePassword.getUsername());
            throw new WrongCredentialException();
        }
        if (!person.canHaveAccessTo(feature)) {
            logger.error("User " + person.getFullName() + " does not have access to " + feature);
            throw new UnauthorizatedException();
        }

    }

    private UsernamePassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new UsernamePassword(username, password);
    }
}
