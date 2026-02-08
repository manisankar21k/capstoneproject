//package com.example.insurance_policy_management_system.service;
//
//import com.example.insurance_policy_management_system.entity.User;
//import com.example.insurance_policy_management_system.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class AuthService {
//    @Autowired
//    private UserRepository userRepository;
//    
//    public User login(String username, String password) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent() && user.get().getPassword().equals(password)) {
//            return user.get();
//        }
//        return null;
//    }
//    
//    public User registerUser(User user) {
//        return userRepository.save(user);
//    }
//    
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//}
