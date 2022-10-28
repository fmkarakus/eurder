package com.switchfully.eurder.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongCredentialException extends RuntimeException {
    public WrongCredentialException() {
        super("Wrong credentials!");
    }
}
