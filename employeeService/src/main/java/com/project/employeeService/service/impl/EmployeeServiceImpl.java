package com.project.employeeService.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.entity.Employee;
import com.project.employeeService.exception.EmailAlreadyExistsException;
import com.project.employeeService.exception.ResourceNotFoundException;
import com.project.employeeService.mapper.EmployeeMapper;
import com.project.employeeService.repository.EmployeeRepository;
import com.project.employeeService.service.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) throws EmailAlreadyExistsException{
        Optional<Employee> userOptional = employeeRepository.findByEmail(employeeDto.getEmail());
        if (userOptional.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for user");
        }
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
        if (savedEmployeeDto == null) {
            throw new RuntimeException("Employee not saved");
        }return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee","ID",id)
        );
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

}
