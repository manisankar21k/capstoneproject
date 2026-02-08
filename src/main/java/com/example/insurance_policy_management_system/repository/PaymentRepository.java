package com.example.insurance_policy_management_system.repository;

import com.example.insurance_policy_management_system.entity.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	@Query("select p from Payment p left join fetch p.customerPolicy cp left join fetch cp.customer left join fetch cp.policy")
	List<Payment> findAllWithCustomerPolicy();
}
