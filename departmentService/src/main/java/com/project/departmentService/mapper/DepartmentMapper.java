package com.project.departmentService.mapper;

import com.project.departmentService.dto.DepartmentDto;
import com.project.departmentService.entity.Department;

public class DepartmentMapper {

    // Converting department JPA entity to DTO
    public static DepartmentDto mapToDepartmentDto(Department department){
        DepartmentDto departmentDto = new DepartmentDto(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );
        return departmentDto;
    }
    
    //Converting department DTO into JPA entity
    public static Department mapToDepartment(DepartmentDto departmentDto){
        Department department = new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode()
        );
        return department;
    }

}
