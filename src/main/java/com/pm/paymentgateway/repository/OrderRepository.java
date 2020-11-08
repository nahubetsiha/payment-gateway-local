package com.pm.paymentgateway.repository;

import com.pm.paymentgateway.model.CardInformation;
import com.pm.paymentgateway.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
