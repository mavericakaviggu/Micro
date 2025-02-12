package com.project.departmentService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.departmentService.dto.DepartmentDto;
import com.project.departmentService.exception.DepartmentCodeAlreadyExistsException;
import com.project.departmentService.service.DepartmentService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    
    private DepartmentService departmentService;

    //build SAVE department REST API
    @PostMapping()
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) throws DepartmentCodeAlreadyExistsException {
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        // amazonq-ignore-next-line
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    //build GET department by code REST API http://localhost:8080/api/departments/IT001
    @GetMapping("/{code}")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable String code) {
        DepartmentDto department = departmentService.getDepartmentByCode(code);
        // amazonq-ignore-next-line
        return new ResponseEntity<>(department, HttpStatus.OK);
    }
    
}
