package com.pm.paymentgateway.service.impl;

import com.pm.paymentgateway.exception.EntityNotFoundException;
import com.pm.paymentgateway.exception.InvalidPaymentException;
import com.pm.paymentgateway.model.*;
import com.pm.paymentgateway.repository.VisaRepository;
import com.pm.paymentgateway.service.CardService;
//import com.pm.paymentgateway.service.RecipientService;
import com.pm.paymentgateway.service.TransactionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("visaService")
public class VisaServiceImpl implements CardService<Visa> {

    VisaRepository visaRepository;
    TransactionService<VisaTransaction> vTransactionService;
//    RecipientService recipientService;

    public VisaServiceImpl(VisaRepository visaRepository,
                           @Qualifier("VTransactionServiceImpl") TransactionService<VisaTransaction> vTransactionService){
        this.visaRepository = visaRepository;
        this.vTransactionService = vTransactionService;
//        this.recipientService = recipientService;
    }

    @Override
    public void processTransaction(Visa visa, List<PayTo> payTo){

        double amount = 0;

        for(PayTo p: payTo){
            amount+=p.getPrice();
        }

        if(visa.getAvailableBalance()-amount < 0) throw new InvalidPaymentException("Insufficient balance to complete transaction");

        for(PayTo p: payTo){
//            Recipient recipient = recipientService.getRecipientByAccountNo(p.getAccountNumber());

            double chargedAmount = p.getPrice()*p.getQuantity();
            visa.setAvailableBalance(visa.getAvailableBalance()-chargedAmount);
            updateCard(visa, visa.getVisaCardId());
//            recipient.setBalance(recipient.getBalance()+amount);
            VisaTransaction visaTransaction = new VisaTransaction();
            visaTransaction.setCard(visa);
            visaTransaction.setChargedAmount(chargedAmount);
            visaTransaction.setDate(LocalDate.now());
//            visaTransaction.setRecipient(recipient);


            vTransactionService.addTransaction(visaTransaction);
        }

//        return visa.getAvailableBalance();

    }

    @Override
    public List<Visa> getAllCards() {

        List<Visa> visas = visaRepository.findAll();

        if(visas == null) throw new EntityNotFoundException(Visa.class);

        return visas;
    }

    @Override
    public Optional<Visa> getCard(Long cardId) {
        Visa visa = visaRepository.getOne(cardId);
        if(visa == null) throw new EntityNotFoundException(Visa.class, cardId);

        return visaRepository.findById(cardId);
    }

    @Override
    public Visa addCard(@Valid Visa visa) {
        return visaRepository.save(visa);
    }

    @Override
    public Visa updateCard(@Valid Visa visa, Long cardId) {

        return visaRepository.findById(cardId)
                .map(cardToUpdate -> {
                    cardToUpdate.setAvailableBalance(visa.getAvailableBalance());
                    cardToUpdate.setCardNumber(visa.getCardNumber());
                    cardToUpdate.setExpDate(visa.getExpDate());
                    cardToUpdate.setName(visa.getName());
                    cardToUpdate.setPin(visa.getPin());

                    return visaRepository.save(cardToUpdate);
                }).orElseThrow(() -> new EntityNotFoundException(Visa.class, cardId));
    }

    @Override
    public Long deleteCard(Long cardId) {
        Visa visa = visaRepository.getOne(cardId);
        if(visa == null) throw new EntityNotFoundException(Visa.class, cardId);

        visaRepository.delete(visa);
        return cardId;
    }

    @Override
    public Visa getByCardNumber(String ccNum) {
        return visaRepository.getByCardNumber(ccNum);
    }
}
