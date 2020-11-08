package com.pm.paymentgateway.service.impl;

import com.pm.paymentgateway.exception.EntityNotFoundException;
import com.pm.paymentgateway.model.MasterCardTransaction;
import com.pm.paymentgateway.repository.MasterCardTransactionRepository;
import com.pm.paymentgateway.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MTransactionServiceImpl implements TransactionService<MasterCardTransaction> {
    
    MasterCardTransactionRepository masterCardTransactionRepository;

    public MTransactionServiceImpl(MasterCardTransactionRepository masterCardTransactionRepository){
        this.masterCardTransactionRepository = masterCardTransactionRepository;
    }
    
    @Override
    public List<MasterCardTransaction> getAllTransactions() {
        List<MasterCardTransaction> masterCards = masterCardTransactionRepository.findAll();

        if(masterCards.isEmpty()) throw new EntityNotFoundException(MasterCardTransaction.class);

        return masterCards;
    }

    @Override
    public Optional<MasterCardTransaction> getTransaction(Long transactionId) {
        return masterCardTransactionRepository.findById(transactionId);
    }

    @Override
    public MasterCardTransaction addTransaction(MasterCardTransaction masterCardTransaction) {
        return masterCardTransactionRepository.save(masterCardTransaction);
    }

    @Override
    public MasterCardTransaction updateTransaction(MasterCardTransaction masterCardTransaction, Long transactionId) {
        return masterCardTransactionRepository.findById(transactionId)
                .map(transactionToUpdate -> {
                    transactionToUpdate.setDate(masterCardTransaction.getDate());
                    transactionToUpdate.setChargedAmount(masterCardTransaction.getChargedAmount());
                    transactionToUpdate.setCard(masterCardTransaction.getCard());
//                    transactionToUpdate.setRecipient(masterCardTransaction.getRecipient());

                    return transactionToUpdate;
                }).orElseThrow(() -> new EntityNotFoundException(MasterCardTransaction.class, transactionId));
    }

    @Override
    public Long deleteTransaction(Long transactionId) {
        MasterCardTransaction masterCardTransaction = masterCardTransactionRepository.getOne(transactionId);
        if(masterCardTransaction == null) throw new EntityNotFoundException(MasterCardTransaction.class, transactionId);

        masterCardTransactionRepository.delete(masterCardTransaction);
        return transactionId;
    }
}
