package com.example.insurance_policy_management_system.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer policyId;
    
    @Column(nullable = false)
    private String policyName;
    
    @Column(nullable = false)
    private String policyType;
    
    @Column(nullable = false)
    private BigDecimal premiumAmount;
    
    private Integer durationMonths;
    private BigDecimal coverageAmount;
    
    // Constructors
    public Policy() {}
    
    public Policy(String policyName, String policyType, BigDecimal premiumAmount, Integer durationMonths, BigDecimal coverageAmount) {
        this.policyName = policyName;
        this.policyType = policyType;
        this.premiumAmount = premiumAmount;
        this.durationMonths = durationMonths;
        this.coverageAmount = coverageAmount;
    }
    
    // Getters and Setters
    public Integer getPolicyId() { return policyId; }
    public void setPolicyId(Integer policyId) { this.policyId = policyId; }
    
    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }
    
    public String getPolicyType() { return policyType; }
    public void setPolicyType(String policyType) { this.policyType = policyType; }
    
    public BigDecimal getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(BigDecimal premiumAmount) { this.premiumAmount = premiumAmount; }
    
    public Integer getDurationMonths() { return durationMonths; }
    public void setDurationMonths(Integer durationMonths) { this.durationMonths = durationMonths; }
    
    public BigDecimal getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(BigDecimal coverageAmount) { this.coverageAmount = coverageAmount; }
}
