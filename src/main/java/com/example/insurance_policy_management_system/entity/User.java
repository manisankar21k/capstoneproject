//package com.example.insurance_policy_management_system.entity;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "users")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer userId;
//    
//    @Column(unique = true, nullable = false)
//    private String username;
//    
//    @Column(nullable = false)
//    private String password;
//    
//    @Column(nullable = false)
//    private String email;
//    
//    private String fullName;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    
//    // Constructors
//    public User() {}
//    
//    public User(String username, String password, String email) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//    }
//    
//    // Getters and Setters
//    public Integer getUserId() { return userId; }
//    public void setUserId(Integer userId) { this.userId = userId; }
//    
//    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }
//    
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//    
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//    
//    public String getFullName() { return fullName; }
//    public void setFullName(String fullName) { this.fullName = fullName; }
//    
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//    
//    public LocalDateTime getUpdatedAt() { return updatedAt; }
//    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
//}
