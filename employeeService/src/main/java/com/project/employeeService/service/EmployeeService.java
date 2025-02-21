package com.project.employeeService.service;

import com.project.employeeService.dto.APIResponseDto;
import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.exception.EmailAlreadyExistsException;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto) throws EmailAlreadyExistsException;
    APIResponseDto getEmployee(Long id);

}
