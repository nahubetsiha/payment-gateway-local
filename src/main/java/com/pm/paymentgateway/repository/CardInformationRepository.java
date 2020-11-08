package com.pm.paymentgateway.repository;

import com.pm.paymentgateway.model.CardInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardInformationRepository extends JpaRepository<CardInformation,Long> {
}
