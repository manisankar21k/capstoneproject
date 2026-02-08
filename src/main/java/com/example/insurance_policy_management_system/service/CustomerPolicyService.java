package com.example.insurance_policy_management_system.service;

import com.example.insurance_policy_management_system.entity.CustomerPolicy;
import com.example.insurance_policy_management_system.repository.CustomerPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerPolicyService {
    @Autowired
    private CustomerPolicyRepository customerPolicyRepository;
    
    public CustomerPolicy assignPolicy(CustomerPolicy customerPolicy) {
        // Auto-generate customerPolicyId if not provided
        if (customerPolicy.getCustomerPolicyId() == null || customerPolicy.getCustomerPolicyId().trim().isEmpty()) {
            customerPolicy.setCustomerPolicyId(generateCustomerPolicyId());
        }
        if (customerPolicy.getStartDate() == null) {
            customerPolicy.setStartDate(LocalDate.now());
        }
        if (customerPolicy.getPolicyStatus() == null) {
            customerPolicy.setPolicyStatus("ACTIVE");
        }
        return customerPolicyRepository.save(customerPolicy);
    }
    
    private String generateCustomerPolicyId() {
        // Generate customerPolicyId in format: CP + timestamp suffix
        // Example: CP1707437400123
        return "CP" + System.currentTimeMillis();
    }
    
    public List<CustomerPolicy> getAllCustomerPolicies() {
        return customerPolicyRepository.findAll();
    }
    
    public CustomerPolicy getCustomerPolicyById(String id) {
        return customerPolicyRepository.findById(id).orElse(null);
    }
    
    public CustomerPolicy updatePolicyStatus(String id, String status) {
        CustomerPolicy policy = getCustomerPolicyById(id);
        if (policy != null) {
            policy.setPolicyStatus(status);
            return customerPolicyRepository.save(policy);
        }
        return null;
    }
    
    public boolean deleteCustomerPolicy(String id) {
        if (customerPolicyRepository.existsById(id)) {
            customerPolicyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
