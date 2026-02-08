package com.example.insurance_policy_management_system.repository;

import com.example.insurance_policy_management_system.entity.CustomerPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerPolicyRepository extends JpaRepository<CustomerPolicy, String> {
}
