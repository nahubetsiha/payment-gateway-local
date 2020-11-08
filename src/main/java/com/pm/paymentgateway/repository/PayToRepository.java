package com.pm.paymentgateway.repository;

import com.pm.paymentgateway.model.PayTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayToRepository extends JpaRepository<PayTo, Long> {
}
