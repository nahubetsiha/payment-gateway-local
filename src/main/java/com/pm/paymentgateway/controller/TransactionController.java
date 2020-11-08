package com.pm.paymentgateway.controller;

//import com.pm.paymentgateway.model.Recipient;
//import com.pm.paymentgateway.service.RecipientService;
import com.pm.paymentgateway.model.MasterCardTransaction;
import com.pm.paymentgateway.model.VisaTransaction;
import com.pm.paymentgateway.service.impl.MTransactionServiceImpl;
import com.pm.paymentgateway.service.impl.VTransactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("api/transactions")
public class TransactionController {

    MTransactionServiceImpl mTransactionService;
    VTransactionServiceImpl vTransactionService;

    public TransactionController(MTransactionServiceImpl mTransactionService, VTransactionServiceImpl vTransactionService){
        this.mTransactionService = mTransactionService;
        this.vTransactionService = vTransactionService;
    }

    @GetMapping("/get-all/mTransaction")
    public ResponseEntity<List<MasterCardTransaction>> getAllMasterCard(){
        return new ResponseEntity<>(mTransactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/get-all/vTransaction")
    public ResponseEntity<List<VisaTransaction>> getAllVisa(){
        return new ResponseEntity<>(vTransactionService.getAllTransactions(), HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<Recipient> addRecipient(@RequestBody @Valid Recipient recipient){
//        return new ResponseEntity<>(recipientService.addRecipient(recipient), HttpStatus.OK);
//    }
//
//    @PutMapping("/edit/{recipientId}")
//    public ResponseEntity<Recipient> editRecipient(@RequestBody @Valid Recipient recipient, @PathVariable  Long recipientId){
//        return new ResponseEntity<>(recipientService.updateRecipient(recipient, recipientId), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/delete/{recipientId}")
//    public ResponseEntity<Long> deleteRecipient(@PathVariable Long recipientId){
//        return new ResponseEntity<>(recipientService.deleteRecipient(recipientId), HttpStatus.OK);
//    }
}
