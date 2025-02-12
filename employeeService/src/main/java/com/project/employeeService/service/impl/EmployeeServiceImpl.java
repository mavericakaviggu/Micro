package com.project.employeeService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.entity.Employee;
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
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
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
                () -> new RuntimeException("Employee not found")
        );
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

}
