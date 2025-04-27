package com.maybank.apiservice.service.impl;

import com.maybank.apiservice.constant.AppStatus;
import com.maybank.apiservice.dto.TransactionRequest;
import com.maybank.apiservice.exception.PaymentException;
import com.maybank.apiservice.model.Account;
import com.maybank.apiservice.model.Transaction;
import com.maybank.apiservice.repository.AccountRepository;
import com.maybank.apiservice.repository.TransactionRepository;
import com.maybank.apiservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new PaymentException(AppStatus.APP_ERROR.getCode(), "Account not found"));

        Transaction txn = new Transaction();
        txn.setAccount(account);
        txn.setAmount(request.getAmount());
        txn.setTxnType(request.getTxnType());

        // Update balance
        BigDecimal newBalance = "DEPOSIT".equalsIgnoreCase(request.getTxnType())
                ? account.getBalance().add(request.getAmount())
                : account.getBalance().subtract(request.getAmount());

        account.setBalance(newBalance);
        accountRepository.save(account);

        return transactionRepository.save(txn);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactions(Long accountId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findByAccount_AccountId(accountId, pageable);
    }
}
