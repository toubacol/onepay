package com.decathlon.onepay.Util;

public interface Constant {
    String API_TRANSACTION = "/api/transactions";
    String INVALID_PAYMENT_TRANSACTION_STATUS = "Invalid payment transaction status";
    String NOT_UPDATABLE_TRANSACTION = "Not updatable transaction";
    String TRANSACTION_NOT_FOUND = "Transaction not found";
    String NOT_UPDATABLE_TRANSACTION_LOG_MESSAGE = "Not updatable transaction : Already captured";
    String INVALID_PAYMENT_TRANSACTION_STATUS_LOG_MESSAGE = "Invalid payment transaction status : Cannot set CAPTURED to none AUTHORIZED transaction";
}
