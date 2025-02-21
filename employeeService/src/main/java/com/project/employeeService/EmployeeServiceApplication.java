package com.project.employeeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients //you dont have to create a bean for feign client
public class EmployeeServiceApplication {

	// @Bean
	// public RestTemplate	getRestTemplate() {
	// 	return new RestTemplate();
	// }

	//configuring web client as spring bean
	// @Bean
	// public WebClient webClient() {
	// 	return WebClient.builder().build();
	// }

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

}
