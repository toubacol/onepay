package com.decathlon.onepay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Transaction not found")
public class TransactionNotFoundException extends TransactionServiceException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
