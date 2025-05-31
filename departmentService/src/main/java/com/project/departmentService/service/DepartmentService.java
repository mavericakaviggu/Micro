package com.project.departmentService.service;

import com.project.departmentService.dto.DepartmentDto;
import com.project.departmentService.exception.DepartmentCodeAlreadyExistsException;
import java.util.List;

public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto departmentDto) throws DepartmentCodeAlreadyExistsException;
    DepartmentDto getDepartmentByCode(String code);
    List<DepartmentDto> getAllDepartments();

}
