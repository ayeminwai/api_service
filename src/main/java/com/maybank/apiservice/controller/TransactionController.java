package com.maybank.apiservice.controller;

import com.maybank.apiservice.dto.ResponseDTO;
import com.maybank.apiservice.dto.TransactionRequest;
import com.maybank.apiservice.model.Transaction;
import com.maybank.apiservice.service.TransactionService;
import com.maybank.apiservice.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Transaction>> createTransaction(@Valid @RequestBody TransactionRequest request) {
        return ResponseUtil.success(transactionService.createTransaction(request));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<Transaction>>> getTransactions(
            @RequestParam Long accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseUtil.success(transactionService.getTransactions(accountId, page, size));
    }
}
