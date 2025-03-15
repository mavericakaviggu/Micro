package com.project.departmentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;

// Swagger API documentation //URL: http://localhost:8080/swagger-ui/index.htm
@OpenAPIDefinition(
		info=@io.swagger.v3.oas.annotations.info.Info(
				title="Department Service API",
				version="1.0",
				description="Documentation Department Service API v1.0",
				contact = @Contact(name = "Vignesh", email = "abc@gmail.com"),
				license = @io.swagger.v3.oas.annotations.info.License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0.html")
				)
)
@SpringBootApplication
// to declare this service as a client of Eureka Server
@EnableDiscoveryClient
public class DepartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentServiceApplication.class, args);
	}

}
