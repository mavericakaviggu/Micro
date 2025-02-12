package com.project.departmentService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DepartmentCodeAlreadyExistsException extends Exception {
    public String message;

    public DepartmentCodeAlreadyExistsException(String message) {
        super(message);
    }
} 