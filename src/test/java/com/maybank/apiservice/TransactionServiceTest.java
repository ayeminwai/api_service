package com.maybank.apiservice;

import com.maybank.apiservice.dto.TransactionRequest;
import com.maybank.apiservice.exception.PaymentException;
import com.maybank.apiservice.model.Account;
import com.maybank.apiservice.model.Transaction;
import com.maybank.apiservice.repository.AccountRepository;
import com.maybank.apiservice.repository.TransactionRepository;
import com.maybank.apiservice.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock account
        account = new Account();
        account.setAccountId(1L);
        account.setBalance(new BigDecimal("1000"));
    }

    @Test
    void testCreateTransactionDeposit() {
        // Create a transaction request for a deposit
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal("200"));
        request.setTxnType("DEPOSIT");

        // Mock account repository to return the account
        when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(account));

        // Mock transaction repository to return a saved transaction
        Transaction savedTransaction = new Transaction();
        savedTransaction.setAccount(account);
        savedTransaction.setAmount(request.getAmount());
        savedTransaction.setTxnType(request.getTxnType());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        // Call the service method
        Transaction transaction = transactionService.createTransaction(request);

        // Verify the interactions and assertions
        verify(accountRepository).findById(1L);
        verify(transactionRepository).save(any(Transaction.class));

        // Assert that the transaction is created and account balance is updated
        assertNotNull(transaction);
        assertEquals(new BigDecimal("1200"), account.getBalance()); // 1000 + 200 deposit
    }

    @Test
    void testCreateTransactionWithdrawal() {
        // Create a transaction request for a withdrawal
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal("200"));
        request.setTxnType("WITHDRAW");

        // Mock account repository to return the account
        when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(account));

        // Mock transaction repository to return a saved transaction
        Transaction savedTransaction = new Transaction();
        savedTransaction.setAccount(account);
        savedTransaction.setAmount(request.getAmount());
        savedTransaction.setTxnType(request.getTxnType());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        // Call the service method
        Transaction transaction = transactionService.createTransaction(request);

        // Verify the interactions and assertions
        verify(accountRepository).findById(1L);
        verify(transactionRepository).save(any(Transaction.class));

        // Assert that the transaction is created and account balance is updated
        assertNotNull(transaction);
        assertEquals(new BigDecimal("800"), account.getBalance()); // 1000 - 200 withdrawal
    }

    @Test
    void testCreateTransactionAccountNotFound() {
        // Create a transaction request
        TransactionRequest request = new TransactionRequest();
        request.setAccountId(999L); // Non-existing account
        request.setAmount(new BigDecimal("200"));
        request.setTxnType("DEPOSIT");

        // Mock account repository to return empty
        when(accountRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // Call the service method and assert exception is thrown
        PaymentException exception = assertThrows(PaymentException.class, () -> transactionService.createTransaction(request));

        // Verify the exception message
        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void testGetTransactions() {
        // Create a mock transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(new BigDecimal("200"));
        transaction.setTxnType("DEPOSIT");

        // Create a page of transactions
        Page<Transaction> transactions = new PageImpl<>(Collections.singletonList(transaction), PageRequest.of(0, 10), 1);

        // Mock transaction repository to return the transactions
        when(transactionRepository.findByAccount_AccountId(1L, PageRequest.of(0, 10))).thenReturn(transactions);

        // Call the service method
        Page<Transaction> result = transactionService.getTransactions(1L, 0, 10);

        // Verify the interactions
        verify(transactionRepository).findByAccount_AccountId(1L, PageRequest.of(0, 10));

        // Assert that the transaction is returned in the page
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(new BigDecimal("200"), result.getContent().get(0).getAmount());
    }

}
