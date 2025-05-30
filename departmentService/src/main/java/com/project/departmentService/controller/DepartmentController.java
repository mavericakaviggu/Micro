package com.project.departmentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.departmentService.dto.DepartmentDto;
import com.project.departmentService.exception.DepartmentCodeAlreadyExistsException;
import com.project.departmentService.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

// Swagger API documentation(usage of @Tag, @Operation, @ApiResponse)
// @Tag is used to declare a tag for the API documentation
@Tag(name = "Department Service - DepartmentController", description = "Department Controller exposes REST APIs for Department Service")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    
    private DepartmentService departmentService;

    //Operation annotation is used to declare an operation for the API documentation
    //Below 2 annotations are used in swagger API documentation
    @Operation(summary = "Save Department REST API", description = "This API saves a department")
    @ApiResponse(responseCode = "201", description = "Department saved successfully")
    //build SAVE department REST API
    @PostMapping()
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) throws DepartmentCodeAlreadyExistsException {
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        // amazonq-ignore-next-line
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Department REST API", description = "This API gets a department")
    @ApiResponse(responseCode = "200", description = "Department fetched successfully")
    //build GET department by code REST API http://localhost:8080/api/departments/IT001
    @GetMapping("/{code}")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable String code) {
        DepartmentDto department = departmentService.getDepartmentByCode(code);
        // amazonq-ignore-next-line
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
    
}
