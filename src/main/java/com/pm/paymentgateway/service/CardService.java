package com.pm.paymentgateway.service;

import com.pm.paymentgateway.model.MasterCard;
import com.pm.paymentgateway.model.MasterCardTransaction;
import com.pm.paymentgateway.model.PayTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CardService<C>{
    void processTransaction(C card, List<PayTo> payTo);
    List<C> getAllCards();
    Optional<C> getCard(Long cardId);
    C addCard(C masterCard);
    C updateCard(C masterCard, Long cardId);
    Long deleteCard(Long cardId);
    C getByCardNumber(String ccNum);
}
