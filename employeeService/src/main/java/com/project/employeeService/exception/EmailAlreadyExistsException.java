package com.project.employeeService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistsException extends Exception {
    public String message;

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
} 