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
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;

@SuppressWarnings("unused")
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    // private RestTemplate restTemplate;
    private WebClient webClient;
    private APIClient apiClient;
    //Logger should not be injected as it is a utility, not a managed spring bean 
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    
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

    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment") 
    @Override
    public APIResponseDto getEmployee(Long id) {
        logger.info("inside getEmployeeById() Method");
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee","ID",id)
        );
        //REST TEMPLATE
        // ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDepartmentCode(), DepartmentDto.class);
        // DepartmentDto departmentDto = responseEntity.getBody();

        //WEB CLIENT
        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/"+employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        //USING FEIGN CLIENT
        //DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(EmployeeMapper.mapToEmployeeDto(employee));
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long id, Throwable throwable) {
        logger.info("inside getDefaultDepartmentMethod");
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentCode("D001");
        departmentDto.setDepartmentName("Default Department");
        departmentDto.setDepartmentDescription("Default Department using fallback method of circuit breaker");

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(EmployeeMapper.mapToEmployeeDto(employeeRepository.findById(id).get()));
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }
}

