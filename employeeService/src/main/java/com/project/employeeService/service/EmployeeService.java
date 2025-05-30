package com.project.employeeService.service;

import com.project.employeeService.dto.APIResponseDto;
import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.exception.EmailAlreadyExistsException;
import java.util.List;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto) throws EmailAlreadyExistsException;
    APIResponseDto getEmployeeById(Long id);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) throws EmailAlreadyExistsException;
    void deleteEmployee(Long id);
}
