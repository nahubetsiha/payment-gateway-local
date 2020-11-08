package com.pm.paymentgateway.repository;

import com.pm.paymentgateway.model.MasterCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCardTransactionRepository extends JpaRepository<MasterCardTransaction, Long> {
}
