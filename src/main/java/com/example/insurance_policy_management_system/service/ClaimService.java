package com.example.insurance_policy_management_system.service;

import com.example.insurance_policy_management_system.entity.Claim;
import com.example.insurance_policy_management_system.entity.CustomerPolicy;
import com.example.insurance_policy_management_system.repository.ClaimRepository;
import com.example.insurance_policy_management_system.repository.CustomerPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private CustomerPolicyRepository customerPolicyRepository;
    
    public Claim createClaim(Claim claim) {
        if (claim.getClaimDate() == null) {
            claim.setClaimDate(LocalDate.now());
        }
        if (claim.getClaimStatus() == null) {
            claim.setClaimStatus("PENDING");
        }
        // Load CustomerPolicy relationship to ensure customer and policy data is available
        if (claim.getCustomerPolicyId() != null) {
            CustomerPolicy customerPolicy = customerPolicyRepository.findById(claim.getCustomerPolicyId()).orElse(null);
            if (customerPolicy != null) {
                claim.setCustomerPolicy(customerPolicy);
            }
        }
        return claimRepository.save(claim);
    }
    
    public List<Claim> getAllClaims() {
        try {
            List<Claim> claims = claimRepository.findAllWithCustomerPolicy();
            // Ensure customerPolicy is loaded for each claim
            for (Claim c : claims) {
                if (c.getCustomerPolicyId() != null && c.getCustomerPolicy() == null) {
                    c.setCustomerPolicy(customerPolicyRepository.findById(c.getCustomerPolicyId()).orElse(null));
                }
            }
            return claims;
        } catch (Exception e) {
            List<Claim> claims = claimRepository.findAll();
            // Fallback: ensure customerPolicy is loaded
            for (Claim c : claims) {
                if (c.getCustomerPolicyId() != null && c.getCustomerPolicy() == null) {
                    c.setCustomerPolicy(customerPolicyRepository.findById(c.getCustomerPolicyId()).orElse(null));
                }
            }
            return claims;
        }
    }
    
    public Claim getClaimById(Integer id) {
        return claimRepository.findById(id).orElse(null);
    }
    
    public Claim updateClaim(Integer id, Claim claim) {
        Claim existing = getClaimById(id);
        if (existing != null) {
            existing.setClaimAmount(claim.getClaimAmount());
            existing.setClaimStatus(claim.getClaimStatus());
            existing.setDescription(claim.getDescription());
            return claimRepository.save(existing);
        }
        return null;
    }

    public Claim updateClaimStatus(Integer id, String status) {
        Claim claim = getClaimById(id);
        if (claim != null) {
            claim.setClaimStatus(status);
            return claimRepository.save(claim);
        }
        return null;
    }
    
    public boolean deleteClaim(Integer id) {
        if (claimRepository.existsById(id)) {
            claimRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
