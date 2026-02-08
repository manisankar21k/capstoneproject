package com.example.insurance_policy_management_system.controller;

import com.example.insurance_policy_management_system.dto.ApiResponse;
import com.example.insurance_policy_management_system.entity.Payment;
import com.example.insurance_policy_management_system.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    
    @PostMapping
    public ResponseEntity<ApiResponse> createPayment(@RequestBody Payment payment) {
        Payment created = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payment created successfully", created));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        try {
            logger.info("getAllPayments response JSON: {}", objectMapper.writeValueAsString(payments));
        } catch (Exception e) {
            logger.warn("Failed to serialize payments for logging", e);
        }
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved successfully", payments));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable Integer id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            try {
                logger.info("getPaymentById response JSON: {}", objectMapper.writeValueAsString(payment));
            } catch (Exception e) {
                logger.warn("Failed to serialize payment for logging", e);
            }
            return ResponseEntity.ok(ApiResponse.success("Payment found", payment));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Payment not found"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePayment(@PathVariable Integer id, 
                                                      @RequestBody Payment payment) {
        Payment updated = paymentService.updatePayment(id, payment);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success("Payment updated successfully", updated));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Payment not found"));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePayment(@PathVariable Integer id) {
        boolean deleted = paymentService.deletePayment(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Payment deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Payment not found"));
    }
}
