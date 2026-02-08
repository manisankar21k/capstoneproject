package com.example.insurance_policy_management_system.service;

import com.example.insurance_policy_management_system.entity.Payment;
import com.example.insurance_policy_management_system.entity.CustomerPolicy;
import com.example.insurance_policy_management_system.repository.PaymentRepository;
import com.example.insurance_policy_management_system.repository.CustomerPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CustomerPolicyRepository customerPolicyRepository;
    
    public Payment createPayment(Payment payment) {
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDate.now());
        }
        if (payment.getPaymentStatus() == null) {
            payment.setPaymentStatus("PENDING");
        }
        // Load CustomerPolicy relationship to ensure customer and policy data is available
        if (payment.getCustomerPolicyId() != null) {
            CustomerPolicy customerPolicy = customerPolicyRepository.findById(payment.getCustomerPolicyId()).orElse(null);
            if (customerPolicy != null) {
                payment.setCustomerPolicy(customerPolicy);
            }
        }
        return paymentRepository.save(payment);
    }
    
    public List<Payment> getAllPayments() {
        try {
            List<Payment> payments = paymentRepository.findAllWithCustomerPolicy();
            // Ensure customerPolicy is loaded for each payment
            for (Payment p : payments) {
                if (p.getCustomerPolicyId() != null && p.getCustomerPolicy() == null) {
                    p.setCustomerPolicy(customerPolicyRepository.findById(p.getCustomerPolicyId()).orElse(null));
                }
            }
            return payments;
        } catch (Exception e) {
            List<Payment> payments = paymentRepository.findAll();
            // Fallback: ensure customerPolicy is loaded
            for (Payment p : payments) {
                if (p.getCustomerPolicyId() != null && p.getCustomerPolicy() == null) {
                    p.setCustomerPolicy(customerPolicyRepository.findById(p.getCustomerPolicyId()).orElse(null));
                }
            }
            return payments;
        }
    }
    
    public Payment getPaymentById(Integer id) {
        return paymentRepository.findById(id).orElse(null);
    }
    
    public Payment updatePayment(Integer id, Payment payment) {
        Payment existing = getPaymentById(id);
        if (existing != null) {
            existing.setAmount(payment.getAmount());
            existing.setPaymentMode(payment.getPaymentMode());
            existing.setPaymentStatus(payment.getPaymentStatus());
            return paymentRepository.save(existing);
        }
        return null;
    }

    public Payment updatePaymentStatus(Integer id, String status) {
        Payment payment = getPaymentById(id);
        if (payment != null) {
            payment.setPaymentStatus(status);
            return paymentRepository.save(payment);
        }
        return null;
    }
    
    public boolean deletePayment(Integer id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
