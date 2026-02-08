package com.example.insurance_policy_management_system.controller;

import com.example.insurance_policy_management_system.dto.ApiResponse;
import com.example.insurance_policy_management_system.entity.Policy;
import com.example.insurance_policy_management_system.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "*")
public class PolicyController {
    @Autowired
    private PolicyService policyService;
    
    @PostMapping
    public ResponseEntity<ApiResponse> createPolicy(@RequestBody Policy policy) {
        Policy created = policyService.createPolicy(policy);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Policy created successfully", created));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPolicies() {
        List<Policy> policies = policyService.getAllPolicies();
        return ResponseEntity.ok(ApiResponse.success("Policies retrieved successfully", policies));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPolicyById(@PathVariable Integer id) {
        Policy policy = policyService.getPolicyById(id);
        if (policy != null) {
            return ResponseEntity.ok(ApiResponse.success("Policy found", policy));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Policy not found"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePolicy(@PathVariable Integer id, 
                                                    @RequestBody Policy policy) {
        Policy updated = policyService.updatePolicy(id, policy);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success("Policy updated successfully", updated));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Policy not found"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePolicy(@PathVariable Integer id) {
        boolean deleted = policyService.deletePolicy(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Policy deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Policy not found"));
    }
}
