package com.switchfully.eurder.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizatedException extends RuntimeException {
    public UnauthorizatedException() {
        super("You do not have the authorization for this feature");
    }
}
