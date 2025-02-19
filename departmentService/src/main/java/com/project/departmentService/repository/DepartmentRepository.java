package com.project.departmentService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.departmentService.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentCode(String departmentCode);

}
