package com.project.employeeService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.employeeService.dto.APIResponseDto;
import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.exception.EmailAlreadyExistsException;
import com.project.employeeService.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
//Annotation for allowing requests from the frontend
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
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

    //build get all employees REST API
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    


}
