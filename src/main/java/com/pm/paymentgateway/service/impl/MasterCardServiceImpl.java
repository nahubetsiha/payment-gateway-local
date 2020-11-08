package com.pm.paymentgateway.service.impl;

import com.pm.paymentgateway.exception.EntityNotFoundException;
import com.pm.paymentgateway.exception.InvalidPaymentException;
import com.pm.paymentgateway.model.MasterCard;
import com.pm.paymentgateway.model.MasterCardTransaction;
import com.pm.paymentgateway.model.PayTo;
//import com.pm.paymentgateway.model.Recipient;
import com.pm.paymentgateway.repository.MasterCardRepository;
import com.pm.paymentgateway.service.CardService;
//import com.pm.paymentgateway.service.RecipientService;
import com.pm.paymentgateway.service.TransactionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("masterCardService")
public class MasterCardServiceImpl implements CardService<MasterCard> {

    MasterCardRepository masterCardRepository;
    TransactionService<MasterCardTransaction> mTransactionService;

    public MasterCardServiceImpl(MasterCardRepository masterCardRepository,
                                 @Qualifier("MTransactionServiceImpl") TransactionService<MasterCardTransaction> mTransactionService){
        this.masterCardRepository = masterCardRepository;
        this.mTransactionService = mTransactionService;
    }


    @Override
    public void processTransaction(MasterCard masterCard, List<PayTo> payTo){

        int amount = 0;

        for(PayTo p: payTo){
            amount+=p.getPrice();
        }

        if(masterCard.getAvailableBalance()-amount < 0) throw new InvalidPaymentException("Insufficient balance to complete transaction");

        for(PayTo p: payTo){

            double chargedAmount = p.getPrice()*p.getQuantity();
            masterCard.setAvailableBalance(masterCard.getAvailableBalance()-chargedAmount);
            updateCard(masterCard, masterCard.getMasterCardId());
            MasterCardTransaction masterCardTransaction = new MasterCardTransaction();
            masterCardTransaction.setCard(masterCard);
            masterCardTransaction.setChargedAmount(chargedAmount);
            masterCardTransaction.setDate(LocalDate.now());


             mTransactionService.addTransaction(masterCardTransaction);
        }
    }


    @Override
    public List<MasterCard> getAllCards() {

        List<MasterCard> masterCards = masterCardRepository.findAll();

        if(masterCards.isEmpty()) throw new EntityNotFoundException(MasterCard.class);

        return masterCards;
    }

    @Override
    public Optional<MasterCard> getCard(Long cardId) {
        return masterCardRepository.findById(cardId);
    }

    @Override
    public MasterCard addCard(@Valid MasterCard masterCard) {
        return masterCardRepository.save(masterCard);
    }

    @Override
    public MasterCard updateCard(@Valid MasterCard masterCard, Long cardId) {

        return masterCardRepository.findById(cardId)
                .map(cardToUpdate -> {
                    cardToUpdate.setAvailableBalance(masterCard.getAvailableBalance());
                    cardToUpdate.setCardNumber(masterCard.getCardNumber());
                    cardToUpdate.setExpDate(masterCard.getExpDate());
                    cardToUpdate.setName(masterCard.getName());
                    cardToUpdate.setPin(masterCard.getPin());

                    return masterCardRepository.save(cardToUpdate);
                }).orElseThrow(() -> new EntityNotFoundException(MasterCard.class, cardId));
    }

    @Override
    public Long deleteCard(Long cardId) {
        MasterCard masterCard = masterCardRepository.getOne(cardId);
        if(masterCard == null) throw new EntityNotFoundException(MasterCard.class, cardId);

        masterCardRepository.delete(masterCard);
        return cardId;
    }

    @Override
    public MasterCard getByCardNumber(String ccNum) {
        return masterCardRepository.getByCardNumber(ccNum);
    }
}
