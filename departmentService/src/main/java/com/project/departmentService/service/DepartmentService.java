package com.project.departmentService.service;

import com.project.departmentService.dto.DepartmentDto;
import com.project.departmentService.exception.DepartmentCodeAlreadyExistsException;

public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto departmentDto) throws DepartmentCodeAlreadyExistsException;

    DepartmentDto getDepartmentByCode(String code);

}
