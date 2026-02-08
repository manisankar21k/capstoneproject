package com.example.insurance_policy_management_system.repository;

import com.example.insurance_policy_management_system.entity.Claim;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
	@Query("select c from Claim c left join fetch c.customerPolicy cp left join fetch cp.customer left join fetch cp.policy")
	List<Claim> findAllWithCustomerPolicy();
}
