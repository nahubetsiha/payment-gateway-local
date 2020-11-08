package com.pm.paymentgateway.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class MasterCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long masterCardId;
    private String cardNumber;
    private String name;
//    @DateTimeFormat(pattern = "yyyy-MM")
    private String expDate;
    private String pin;
    private double availableBalance;
    private CardType cardType = CardType.MASTERCARD;

}
