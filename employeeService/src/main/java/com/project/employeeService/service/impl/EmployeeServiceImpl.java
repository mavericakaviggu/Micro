package com.project.employeeService.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.employeeService.dto.APIResponseDto;
import com.project.employeeService.dto.DepartmentDto;
import com.project.employeeService.dto.EmployeeDto;
import com.project.employeeService.entity.Employee;
import com.project.employeeService.exception.EmailAlreadyExistsException;
import com.project.employeeService.exception.ResourceNotFoundException;
import com.project.employeeService.mapper.EmployeeMapper;
import com.project.employeeService.repository.EmployeeRepository;
import com.project.employeeService.service.APIClient;
import com.project.employeeService.service.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    // private RestTemplate restTemplate;
    // private WebClient webClient;
    private APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) throws EmailAlreadyExistsException{
        Optional<Employee> userOptional = employeeRepository.findByEmail(employeeDto.getEmail());
        if (userOptional.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for user");
        }
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
        if (savedEmployeeDto == null) {
            throw new RuntimeException("Employee not saved");
        }return savedEmployeeDto;
    }

    @Override
    public APIResponseDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee","ID",id)
        );
        //REST TEMPLATE
        // ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDepartmentCode(), DepartmentDto.class);
        // DepartmentDto departmentDto = responseEntity.getBody();

        //WEB CLIENT
        // DepartmentDto departmentDto = webClient.get()
        //         .uri("http://localhost:8080/api/departments/"+employee.getDepartmentCode())
        //         .retrieve()
        //         .bodyToMono(DepartmentDto.class)
        //         .block();

        //USING FEIGN CLIENT
        DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(EmployeeMapper.mapToEmployeeDto(employee));
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }

}
