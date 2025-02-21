package com.project.employeeService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.employeeService.dto.DepartmentDto;

@FeignClient(url = "http://localhost:8080", name = "department-service")
public interface APIClient {

    //build GET department by code REST API http://localhost:8080/api/departments/IT001
    @GetMapping("api/departments/{code}")
    DepartmentDto getDepartmentByCode(@PathVariable String code);

}
