package com.maybank.apiservice.controller;

import com.maybank.apiservice.dto.AccountRequest;
import com.maybank.apiservice.dto.ResponseDTO;
import com.maybank.apiservice.model.Account;
import com.maybank.apiservice.service.AccountService;
import com.maybank.apiservice.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Account>> createAccount(@Valid @RequestBody AccountRequest request) {
        return ResponseUtil.success(accountService.createAccount(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Account>> getAccount(@PathVariable Long id) {
        return ResponseUtil.success(accountService.getAccount(id));
    }
}
