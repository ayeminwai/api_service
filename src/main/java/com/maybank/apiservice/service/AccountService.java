package com.maybank.apiservice.service;

import com.maybank.apiservice.dto.AccountRequest;
import com.maybank.apiservice.model.Account;

public interface AccountService {
    Account createAccount(AccountRequest request);

    Account getAccount(Long id);

}
