package com.employee_management_backend_Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;

@SpringBootApplication
public class
EmployeeManagementBackendApplicationApiApplication  {
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(EmployeeManagementBackendApplicationApiApplication.class, args);
	}
}