package com.pm.paymentgateway.repository;

import com.pm.paymentgateway.model.MasterCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCardRepository extends JpaRepository<MasterCard, Long> {
    MasterCard getByCardNumber(String ccNum);
}
