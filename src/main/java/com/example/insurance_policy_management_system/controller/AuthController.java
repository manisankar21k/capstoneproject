//package com.example.insurance_policy_management_system.controller;
//
//import com.example.insurance_policy_management_system.dto.ApiResponse;
//import com.example.insurance_policy_management_system.dto.LoginRequest;
//import com.example.insurance_policy_management_system.dto.LoginResponse;
//import com.example.insurance_policy_management_system.entity.User;
//import com.example.insurance_policy_management_system.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*")
//public class AuthController {
//    @Autowired
//    private AuthService authService;
//    
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
//        // Validate request
//        if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty() ||
//            loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error("Username and password are required"));
//        }
//        
//        User user = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
//        
//        if (user != null) {
//            LoginResponse response = new LoginResponse(true, "Login successful", user.getUsername(), user.getEmail());
//            return ResponseEntity.ok(ApiResponse.success("Login successful", response));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(ApiResponse.error("Invalid username or password"));
//        }
//    }
//    
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
//        // Validate request
//        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
//            user.getPassword() == null || user.getPassword().trim().isEmpty() ||
//            user.getEmail() == null || user.getEmail().trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error("Username, password, and email are required"));
//        }
//        
//        // Check if username already exists
//        if (authService.findByUsername(user.getUsername()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(ApiResponse.error("Username already exists"));
//        }
//        
//        User registeredUser = authService.registerUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ApiResponse.success("User registered successfully", registeredUser));
//    }
//}
