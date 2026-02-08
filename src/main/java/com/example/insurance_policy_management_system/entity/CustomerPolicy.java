package com.example.insurance_policy_management_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customer_policies")
public class CustomerPolicy {
    @Id
    private String customerPolicyId;
    
    @Column(name = "customer_id")
    private Integer customerId;
    
    @Column(name = "policy_id")
    private Integer policyId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false, nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_id", insertable = false, updatable = false, nullable = false)
    private Policy policy;
    
    private LocalDate startDate;
//    private LocalDate endDate;
    private String policyStatus;
    
    // Constructors
    public CustomerPolicy() {}
    
    public CustomerPolicy(Customer customer, Policy policy) {
        this.customer = customer;
        this.policy = policy;
        this.policyStatus = "ACTIVE";
        this.startDate = LocalDate.now();
    }
    
    // Getters and Setters
    public String getCustomerPolicyId() { return customerPolicyId; }
    public void setCustomerPolicyId(String customerPolicyId) { this.customerPolicyId = customerPolicyId; }
    
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    
    public Integer getPolicyId() { return policyId; }
    public void setPolicyId(Integer policyId) { this.policyId = policyId; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Policy getPolicy() { return policy; }
    public void setPolicy(Policy policy) { this.policy = policy; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
//    public LocalDate getEndDate() { return endDate; }
//    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public String getPolicyStatus() { return policyStatus; }
    public void setPolicyStatus(String policyStatus) { this.policyStatus = policyStatus; }
}
