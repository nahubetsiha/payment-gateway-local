package com.pm.paymentgateway.repository;

import com.pm.paymentgateway.model.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
    Visa getByCardNumber(String ccNum);
}
