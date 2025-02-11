package com.project.employeeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.employeeService.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);

}
