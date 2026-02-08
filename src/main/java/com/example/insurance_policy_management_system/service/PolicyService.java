package com.example.insurance_policy_management_system.service;

import com.example.insurance_policy_management_system.entity.Policy;
import com.example.insurance_policy_management_system.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository policyRepository;
    
    public Policy createPolicy(Policy policy) {
        return policyRepository.save(policy);
    }
    
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }
    
    public Policy getPolicyById(Integer id) {
        return policyRepository.findById(id).orElse(null);
    }
    
    public Policy updatePolicy(Integer id, Policy policy) {
        Policy existing = getPolicyById(id);
        if (existing != null) {
            existing.setPolicyName(policy.getPolicyName());
            existing.setPolicyType(policy.getPolicyType());
            existing.setPremiumAmount(policy.getPremiumAmount());
            existing.setDurationMonths(policy.getDurationMonths());
            existing.setCoverageAmount(policy.getCoverageAmount());
            return policyRepository.save(existing);
        }
        return null;
    }
    
    public boolean deletePolicy(Integer id) {
        if (policyRepository.existsById(id)) {
            policyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
