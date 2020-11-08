package com.pm.paymentgateway.service.impl;

import com.pm.paymentgateway.exception.EntityNotFoundException;
import com.pm.paymentgateway.model.VisaTransaction;
import com.pm.paymentgateway.repository.VisaTransactionRepository;
import com.pm.paymentgateway.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VTransactionServiceImpl implements TransactionService<VisaTransaction> {
    VisaTransactionRepository visaTransactionRepository;

    public VTransactionServiceImpl(VisaTransactionRepository visaTransactionRepository){
        this.visaTransactionRepository = visaTransactionRepository;
    }

    @Override
    public List<VisaTransaction> getAllTransactions() {
        List<VisaTransaction> masterCards = visaTransactionRepository.findAll();

        if(masterCards.isEmpty()) throw new EntityNotFoundException(VisaTransaction.class);

        return masterCards;
    }

    @Override
    public Optional<VisaTransaction> getTransaction(Long transactionId) {
        return visaTransactionRepository.findById(transactionId);
    }

    @Override
    public VisaTransaction addTransaction(VisaTransaction visaTransaction) {
        return visaTransactionRepository.save(visaTransaction);
    }

    @Override
    public VisaTransaction updateTransaction(VisaTransaction visaTransaction, Long transactionId) {
        return visaTransactionRepository.findById(transactionId)
                .map(transactionToUpdate -> {
                    transactionToUpdate.setDate(visaTransaction.getDate());
                    transactionToUpdate.setChargedAmount(visaTransaction.getChargedAmount());
                    transactionToUpdate.setCard(visaTransaction.getCard());
//                    transactionToUpdate.setRecipient(visaTransaction.getRecipient());

                    return transactionToUpdate;
                }).orElseThrow(() -> new EntityNotFoundException(VisaTransaction.class, transactionId));
    }

    @Override
    public Long deleteTransaction(Long transactionId) {
        VisaTransaction visaTransaction = visaTransactionRepository.getOne(transactionId);
        if(visaTransaction == null) throw new EntityNotFoundException(VisaTransaction.class, transactionId);

        visaTransactionRepository.delete(visaTransaction);
        return transactionId;
    }
}
