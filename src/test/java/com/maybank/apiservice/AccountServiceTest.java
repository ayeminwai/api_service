package com.maybank.apiservice;

import com.maybank.apiservice.constant.AppStatus;
import com.maybank.apiservice.dto.AccountRequest;
import com.maybank.apiservice.exception.PaymentException;
import com.maybank.apiservice.model.Account;
import com.maybank.apiservice.model.User;
import com.maybank.apiservice.repository.AccountRepository;
import com.maybank.apiservice.repository.UserRepository;
import com.maybank.apiservice.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private AccountRequest accountRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Test User");

        accountRequest = new AccountRequest();
        accountRequest.setUserId(1L);
        accountRequest.setType("SAVINGS");
    }

    @Test
    void testCreateAccountSuccess() {
        // Mock userRepository to return a user when a user is found by ID
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Mock accountRepository to return the saved account
        Account savedAccount = new Account();
        savedAccount.setAccountId(1L);
        savedAccount.setUser(user);
        savedAccount.setType("SAVINGS");
        savedAccount.setBalance(BigDecimal.ZERO);

        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        // Call the service method
        Account createdAccount = accountService.createAccount(accountRequest);

        // Verify the interactions and assertions
        verify(userRepository).findById(1L);
        verify(accountRepository).save(any(Account.class));

        assertNotNull(createdAccount);
        assertEquals(savedAccount.getAccountId(), createdAccount.getAccountId());
        assertEquals("SAVINGS", createdAccount.getType());
        assertEquals(BigDecimal.ZERO, createdAccount.getBalance());
    }

    @Test
    void testCreateAccountUserNotFound() {
        // Mock userRepository to return empty (user not found)
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect PaymentException
        PaymentException exception = assertThrows(PaymentException.class, () -> {
            accountService.createAccount(accountRequest);
        });

        assertEquals(AppStatus.APP_ERROR.getCode(), exception.getCode());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetAccountSuccess() {
        // Mock accountRepository to return an account
        Account account = new Account();
        account.setAccountId(1L);
        account.setType("SAVINGS");
        account.setBalance(BigDecimal.ZERO);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Call the service method
        Account fetchedAccount = accountService.getAccount(1L);

        // Verify the interactions and assertions
        verify(accountRepository).findById(1L);

        assertNotNull(fetchedAccount);
        assertEquals(1L, fetchedAccount.getAccountId());
        assertEquals("SAVINGS", fetchedAccount.getType());
        assertEquals(BigDecimal.ZERO, fetchedAccount.getBalance());
    }

    @Test
    void testGetAccountNotFound() {
        // Mock accountRepository to return empty (account not found)
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect PaymentException
        PaymentException exception = assertThrows(PaymentException.class, () -> {
            accountService.getAccount(1L);
        });

        assertEquals(AppStatus.APP_ERROR.getCode(), exception.getCode());
        assertEquals("Account not found", exception.getMessage());
    }
}


