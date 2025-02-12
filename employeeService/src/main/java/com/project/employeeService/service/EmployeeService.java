package com.project.employeeService.service;

import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.exception.EmailAlreadyExistsException;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto) throws EmailAlreadyExistsException;
    EmployeeDto getEmployee(Long id);

}
