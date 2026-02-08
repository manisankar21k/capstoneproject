package com.example.insurance_policy_management_system.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    
    @Column(name = "customer_policy_id")
    private String customerPolicyId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_policy_id", insertable = false, updatable = false)
    private CustomerPolicy customerPolicy;
    
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String paymentMode;
    private String paymentStatus;
    
    // Constructors
    public Payment() {}
    
    public Payment(CustomerPolicy customerPolicy, BigDecimal amount) {
        this.customerPolicy = customerPolicy;
        this.amount = amount;
        this.paymentStatus = "PENDING";
        this.paymentDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Integer getPaymentId() { return paymentId; }
    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }
    
    public String getCustomerPolicyId() { return customerPolicyId; }
    public void setCustomerPolicyId(String customerPolicyId) { this.customerPolicyId = customerPolicyId; }
    
    public CustomerPolicy getCustomerPolicy() { return customerPolicy; }
    public void setCustomerPolicy(CustomerPolicy customerPolicy) { this.customerPolicy = customerPolicy; }
    
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
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
