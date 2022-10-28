package com.switchfully.eurder.api;

import com.switchfully.eurder.service.UnauthorizatedException;
import com.switchfully.eurder.service.WrongCredentialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandeling extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    protected void courseID(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizatedException.class)
    protected void courseID(UnauthorizatedException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(FORBIDDEN.value(), ex.getMessage());
    }

    @ExceptionHandler(WrongCredentialException.class)
    protected void courseID(WrongCredentialException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(UNAUTHORIZED.value(), ex.getMessage());
    }


}
