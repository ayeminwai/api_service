package com.maybank.apiservice.service;

import com.maybank.apiservice.dto.TransactionRequest;
import com.maybank.apiservice.model.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {
    Transaction createTransaction(TransactionRequest request);

    Page<Transaction> getTransactions(Long accountId, int page, int size);

}
