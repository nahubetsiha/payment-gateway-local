package com.pm.paymentgateway.repository;

import com.pm.paymentgateway.model.VisaTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaTransactionRepository extends JpaRepository<VisaTransaction, Long> {
}
