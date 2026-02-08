package com.example.insurance_policy_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.insurance_policy_management_system"})
public class InsurancePolicyManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsurancePolicyManagementSystemApplication.class, args);
        System.out.println("✅ Insurance Policy Management System Started!");
        System.out.println("🌐 API Base URL: http://localhost:8080/api");
        System.out.println("📝 Login Endpoint: POST /api/auth/login");
        System.out.println("📝 Default Credentials: username=admin, password=admin123");
    }
}