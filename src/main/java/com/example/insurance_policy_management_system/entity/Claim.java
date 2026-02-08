package com.example.insurance_policy_management_system.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer claimId;
    
    @Column(name = "customer_policy_id")
    private String customerPolicyId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_policy_id", insertable = false, updatable = false)
    private CustomerPolicy customerPolicy;
    
    private LocalDate claimDate;
    private BigDecimal claimAmount;
    private String claimStatus;
    private String description;
    
    // Constructors
    public Claim() {}
    
    public Claim(CustomerPolicy customerPolicy, BigDecimal claimAmount) {
        this.customerPolicy = customerPolicy;
        this.claimAmount = claimAmount;
        this.claimStatus = "PENDING";
        this.claimDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Integer getClaimId() { return claimId; }
    public void setClaimId(Integer claimId) { this.claimId = claimId; }
    
    public String getCustomerPolicyId() { return customerPolicyId; }
    public void setCustomerPolicyId(String customerPolicyId) { this.customerPolicyId = customerPolicyId; }
    
    public CustomerPolicy getCustomerPolicy() { return customerPolicy; }
    public void setCustomerPolicy(CustomerPolicy customerPolicy) { this.customerPolicy = customerPolicy; }
    
    public LocalDate getClaimDate() { return claimDate; }
    public void setClaimDate(LocalDate claimDate) { this.claimDate = claimDate; }
    
    public BigDecimal getClaimAmount() { return claimAmount; }
    public void setClaimAmount(BigDecimal claimAmount) { this.claimAmount = claimAmount; }
    
    public String getClaimStatus() { return claimStatus; }
    public void setClaimStatus(String claimStatus) { this.claimStatus = claimStatus; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    // Derived properties for frontend convenience
    @JsonProperty("customer")
    @JsonInclude
    public Customer getCustomer() { 
        return customerPolicy != null ? customerPolicy.getCustomer() : null;
    }
    
    @JsonProperty("policy")
    @JsonInclude
    public Policy getPolicy() {
        return customerPolicy != null ? customerPolicy.getPolicy() : null;
    }
}
