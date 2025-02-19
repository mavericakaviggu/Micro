package com.project.employeeService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.employeeService.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

}
