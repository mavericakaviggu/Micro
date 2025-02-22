package com.project.employeeService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.employeeService.dto.APIResponseDto;
import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.exception.EmailAlreadyExistsException;
import com.project.employeeService.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //build save employee REST API
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) throws EmailAlreadyExistsException{
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        // amazonq-ignore-next-line
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    
    //build get employee REST API //updated to include department service value in the response
    @GetMapping("/{id}")
    public ResponseEntity<APIResponseDto> getEmployee(@PathVariable("id") Long employeeId){
        APIResponseDto apiResponseDto = employeeService.getEmployee(employeeId);
        return ResponseEntity.ok(apiResponseDto);  
    }
    


}
