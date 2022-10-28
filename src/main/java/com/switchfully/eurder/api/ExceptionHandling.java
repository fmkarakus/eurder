package com.switchfully.eurder.api;

import com.switchfully.eurder.service.UnauthorizatedException;
import com.switchfully.eurder.service.WrongCredentialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentExceptionHandler(IllegalArgumentException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizatedException.class)
    protected void unauthorizatedException(UnauthorizatedException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(FORBIDDEN.value(), ex.getMessage());
    }

    @ExceptionHandler(WrongCredentialException.class)
    protected void courseID(WrongCredentialException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(UNAUTHORIZED.value(), ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            logger.error(error.getDefaultMessage());
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
