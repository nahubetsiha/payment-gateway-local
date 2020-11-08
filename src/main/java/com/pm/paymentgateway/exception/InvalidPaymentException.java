package com.pm.paymentgateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class InvalidPaymentException extends RuntimeException{
        public InvalidPaymentException(String message){
            super(message);
        }
}
