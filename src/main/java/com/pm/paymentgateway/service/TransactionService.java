package com.pm.paymentgateway.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService<T> {
    List<T> getAllTransactions();
    Optional<T> getTransaction(Long transactionId);
    T addTransaction(T transaction);
    T updateTransaction(T transaction, Long transactionId);
    Long deleteTransaction(Long transactionId);
}
