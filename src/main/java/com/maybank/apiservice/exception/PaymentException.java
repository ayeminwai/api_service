package com.maybank.apiservice.exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PaymentException extends RuntimeException {
    private final String code;
    private final String message;

    public PaymentException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
