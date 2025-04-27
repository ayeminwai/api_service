package com.maybank.apiservice.service.impl;

import com.maybank.apiservice.constant.AppStatus;
import com.maybank.apiservice.dto.AccountRequest;
import com.maybank.apiservice.exception.PaymentException;
import com.maybank.apiservice.model.Account;
import com.maybank.apiservice.model.User;
import com.maybank.apiservice.repository.AccountRepository;
import com.maybank.apiservice.repository.UserRepository;
import com.maybank.apiservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Account createAccount(AccountRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new PaymentException(AppStatus.APP_ERROR.getCode(), "User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setType(request.getType());
        account.setBalance(BigDecimal.ZERO);

        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new PaymentException(AppStatus.APP_ERROR.getCode(), "Account not found"));
    }
}
