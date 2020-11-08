package com.pm.paymentgateway.controller;

import com.pm.paymentgateway.exception.EntityNotFoundException;
import com.pm.paymentgateway.model.MasterCard;
import com.pm.paymentgateway.model.MasterCardTransaction;
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
@RequestMapping("api/mastercard")
public class MasterCardController {

    CardService<MasterCard> masterCardService;

    public MasterCardController(@Qualifier("masterCardService") CardService masterCardService){
        this.masterCardService = masterCardService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MasterCard>> getAll(){

        try {
            return new ResponseEntity<>(masterCardService.getAllCards(), HttpStatus.OK);
        } catch (EntityNotFoundException exception){
            throw new EntityNotFoundException(MasterCard.class);
        }

//        return new ResponseEntity<>(masterCardService.getAllCards(), HttpStatus.OK);
    }

    @GetMapping("/get/{cardId}")
    public ResponseEntity<MasterCard> getCardById(@PathVariable Long cardId){
//        return new ResponseEntity<>(masterCardService.getCard(cardId), HttpStatus.OK);
        MasterCard masterCard = masterCardService.getCard(cardId)
                .orElseThrow(()-> new EntityNotFoundException(MasterCard.class, cardId));
        return new ResponseEntity<>(masterCard, HttpStatus.OK);
//                .orElseThrow(()-> new EntityNotFoundException(MasterCard.class, cardId)), HttpStatus.OK);
////                .orElseThrow(()-> new EntityNotFoundException(MasterCard.class, cardId));
    }

    @PostMapping("/add")
    public ResponseEntity<MasterCard> addMasterCard(@RequestBody @Valid MasterCard recipient){
        return new ResponseEntity<>(masterCardService.addCard(recipient), HttpStatus.OK);
    }

    @PutMapping("/edit/{cardId}")
    public ResponseEntity<MasterCard> editMasterCard(@RequestBody @Valid MasterCard recipient, @PathVariable  Long cardId){
        return new ResponseEntity<>(masterCardService.updateCard(recipient, cardId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cardId}")
    public ResponseEntity<Long> deleteMasterCard(@PathVariable Long cardId){
        return new ResponseEntity<>(masterCardService.deleteCard(cardId), HttpStatus.OK);
    }

}
