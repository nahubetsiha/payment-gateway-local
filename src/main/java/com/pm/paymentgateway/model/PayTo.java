package com.pm.paymentgateway.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PayTo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private Long productId;
    private int quantity;
}
