package com.project.departmentService.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; 

import org.springframework.stereotype.Service;

import com.project.departmentService.dto.DepartmentDto;
import com.project.departmentService.entity.Department;
import com.project.departmentService.exception.DepartmentCodeAlreadyExistsException;
import com.project.departmentService.exception.ResourceNotFoundException;
import com.project.departmentService.mapper.DepartmentMapper;
import com.project.departmentService.repository.DepartmentRepository;
import com.project.departmentService.service.DepartmentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    
    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) throws DepartmentCodeAlreadyExistsException {
        Optional<Department> userOptional = departmentRepository.findByDepartmentCode(departmentDto.getDepartmentCode());
        if (userOptional.isPresent()) {
            throw new DepartmentCodeAlreadyExistsException("Department code  already exists");
        }
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
                .orElseThrow(() -> new ResourceNotFoundException("Department", "code", code));
        return DepartmentMapper.mapToDepartmentDto(department);   
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
            .map(DepartmentMapper::mapToDepartmentDto)
            .collect(Collectors.toList());
}
}
