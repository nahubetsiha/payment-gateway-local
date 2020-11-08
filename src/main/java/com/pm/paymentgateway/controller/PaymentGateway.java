package com.pm.paymentgateway.controller;

import com.pm.paymentgateway.exception.InvalidPaymentException;
import com.pm.paymentgateway.model.CardInformation;
import com.pm.paymentgateway.model.Order;
import com.pm.paymentgateway.service.impl.PaymentGatewayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("api/payment")
public class PaymentGateway {

    PaymentGatewayService paymentGatewayService;

    public PaymentGateway(PaymentGatewayService paymentGatewayService){
        this.paymentGatewayService = paymentGatewayService;
    }

    @PostMapping("/process-transaction")
    public <T> ResponseEntity<T> processTransaction(@RequestBody Order order){

        try {
            return new ResponseEntity<T>((T) paymentGatewayService.processTransaction(order), HttpStatus.OK);
        } catch (InvalidPaymentException exception){
            throw new InvalidPaymentException("Payment Transaction failed");
        }

    }

    @PostMapping("/verify-card")
    public <T> ResponseEntity<T> verifyCard(@RequestBody CardInformation cardInformation){

        try {
            return new ResponseEntity<T>((T) paymentGatewayService.verifyCard(cardInformation), HttpStatus.OK);
        } catch (InvalidPaymentException exception){
            throw new InvalidPaymentException(exception.getMessage());
        }

    }
}
