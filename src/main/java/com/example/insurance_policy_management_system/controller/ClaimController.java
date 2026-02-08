package com.example.insurance_policy_management_system.controller;

import com.example.insurance_policy_management_system.dto.ApiResponse;
import com.example.insurance_policy_management_system.entity.Claim;
import com.example.insurance_policy_management_system.service.ClaimService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimController {
    @Autowired
    private ClaimService claimService;
    private static final Logger logger = LoggerFactory.getLogger(ClaimController.class);
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    
    @PostMapping
    public ResponseEntity<ApiResponse> createClaim(@RequestBody Claim claim) {
        Claim created = claimService.createClaim(claim);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Claim created successfully", created));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllClaims() {
        List<Claim> claims = claimService.getAllClaims();
        try {
            logger.info("getAllClaims response JSON: {}", objectMapper.writeValueAsString(claims));
        } catch (Exception e) {
            logger.warn("Failed to serialize claims for logging", e);
        }
        return ResponseEntity.ok(ApiResponse.success("Claims retrieved successfully", claims));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getClaimById(@PathVariable Integer id) {
        Claim claim = claimService.getClaimById(id);
        if (claim != null) {
            try {
                logger.info("getClaimById response JSON: {}", objectMapper.writeValueAsString(claim));
            } catch (Exception e) {
                logger.warn("Failed to serialize claim for logging", e);
            }
            return ResponseEntity.ok(ApiResponse.success("Claim found", claim));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Claim not found"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateClaim(@PathVariable Integer id, 
                                                    @RequestBody Claim claim) {
        Claim updated = claimService.updateClaim(id, claim);
        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.success("Claim updated successfully", updated));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Claim not found"));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteClaim(@PathVariable Integer id) {
        boolean deleted = claimService.deleteClaim(id);
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Claim deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Claim not found"));
    }
}
