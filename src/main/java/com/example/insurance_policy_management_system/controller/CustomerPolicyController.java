package com.example.insurance_policy_management_system.controller;

import com.example.insurance_policy_management_system.dto.ApiResponse;
import com.example.insurance_policy_management_system.entity.CustomerPolicy;
import com.example.insurance_policy_management_system.service.CustomerPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customer-policies")
@CrossOrigin(origins = "*")
public class CustomerPolicyController {
    @Autowired
    private CustomerPolicyService customerPolicyService;
    
    @PostMapping
    public ResponseEntity<ApiResponse> assignPolicy(@RequestBody CustomerPolicy customerPolicy) {
        CustomerPolicy assigned = customerPolicyService.assignPolicy(customerPolicy);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Policy assigned to customer successfully", assigned));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCustomerPolicies() {
        List<CustomerPolicy> policies = customerPolicyService.getAllCustomerPolicies();
        return ResponseEntity.ok(ApiResponse.success("Customer policies retrieved successfully", policies));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCustomerPolicyById(@PathVariable String id) {
        CustomerPolicy policy = customerPolicyService.getCustomerPolicyById(id);
        if (policy != null) {
            return ResponseEntity.ok(ApiResponse.success("Customer policy found", policy));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Customer policy not found"));
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updatePolicyStatus(@PathVariable String id, 
                                                          @RequestParam String status) {
        CustomerPolicy updated = customerPolicyService.updatePolicyStatus(id, status);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success("Policy status updated successfully", updated));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Customer policy not found"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCustomerPolicy(@PathVariable String id) {
        boolean deleted = customerPolicyService.deleteCustomerPolicy(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Customer policy deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Customer policy not found"));
    }
}
