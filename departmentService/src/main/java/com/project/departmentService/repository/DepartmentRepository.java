package com.project.departmentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.departmentService.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByDepartmentCode(String departmentCode);

}
