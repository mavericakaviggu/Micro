package com.project.departmentService.service.impl;

import org.springframework.stereotype.Service;

import com.project.departmentService.dto.DepartmentDto;
import com.project.departmentService.entity.Department;
import com.project.departmentService.mapper.DepartmentMapper;
import com.project.departmentService.repository.DepartmentRepository;
import com.project.departmentService.service.DepartmentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    
    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        //convert department dto to department entity
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);

        //convert department entity to department dto
        DepartmentDto savedDepartmentDto = DepartmentMapper.mapToDepartmentDto(savedDepartment);
        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {
        Department department = departmentRepository.findByDepartmentCode(code)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return DepartmentMapper.mapToDepartmentDto(department);   
    }
}
