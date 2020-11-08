package com.pm.paymentgateway.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visaCardId;
    private String cardNumber;
    private String name;
//    @DateTimeFormat(pattern = "yyyy-MM")
    private String expDate;
    private String pin;
    private double availableBalance;
    private CardType cardType = CardType.VISA;
}
