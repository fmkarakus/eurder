package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.users.Feature;
import com.switchfully.eurder.domain.users.User;
import com.switchfully.eurder.repositories.UserRepository;
import com.switchfully.eurder.domain.users.UsernamePassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateAuthorization(String authorization, Feature feature) {
        UsernamePassword usernamePassword = getUsernamePassword(authorization);
        User user = userRepository.getPersonbyEmail(usernamePassword.getUsername()).orElseGet(() -> {
            logger.error("Wrong credentials");
            throw new WrongCredentialException();
        });

        if (!user.doesPasswordMatch(usernamePassword.getPassword())) {
            logger.error("Password does not match for user " + usernamePassword.getUsername());
            throw new WrongCredentialException();
        }
        if (!user.canHaveAccessTo(feature)) {
            logger.error("User " + user.getFullName() + " does not have access to " + feature);
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
