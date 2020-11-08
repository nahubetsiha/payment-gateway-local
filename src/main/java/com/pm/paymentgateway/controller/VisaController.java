package com.pm.paymentgateway.controller;

import com.pm.paymentgateway.exception.EntityNotFoundException;
import com.pm.paymentgateway.model.Visa;
import com.pm.paymentgateway.model.VisaTransaction;
import com.pm.paymentgateway.service.CardService;
import com.pm.paymentgateway.service.TransactionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("api/visa")
public class VisaController {
    CardService<Visa> visaService;

    public VisaController(@Qualifier("visaService") CardService visaService){
        this.visaService = visaService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Visa>> getAll(){

        try {
            return new ResponseEntity<>(visaService.getAllCards(), HttpStatus.OK);
        } catch (EntityNotFoundException exception){
            throw new EntityNotFoundException(Visa.class);
        }

//        return new ResponseEntity<>(visaService.getAllCards(), HttpStatus.OK);
    }

    @GetMapping("/get/{cardId}")
    public ResponseEntity<Visa> getCardById(@PathVariable Long cardId){
//        return new ResponseEntity<>(visaService.getCard(cardId), HttpStatus.OK);
        Visa visa = visaService.getCard(cardId)
                .orElseThrow(()-> new EntityNotFoundException(Visa.class, cardId));
        return new ResponseEntity<>(visa, HttpStatus.OK);
//                .orElseThrow(()-> new EntityNotFoundException(Visa.class, cardId)), HttpStatus.OK);
////                .orElseThrow(()-> new EntityNotFoundException(Visa.class, cardId));
    }

    @PostMapping("/add")
    public ResponseEntity<Visa> addVisa(@RequestBody @Valid Visa recipient){
        return new ResponseEntity<>(visaService.addCard(recipient), HttpStatus.OK);
    }

    @PutMapping("/edit/{cardId}")
    public ResponseEntity<Visa> editVisa(@RequestBody @Valid Visa recipient, @PathVariable  Long cardId){
        return new ResponseEntity<>(visaService.updateCard(recipient, cardId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<Long> deleteVisa(@PathVariable Long cardId){
        return new ResponseEntity<>(visaService.deleteCard(cardId), HttpStatus.OK);
    }
}
