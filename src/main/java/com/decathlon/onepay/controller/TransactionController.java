package com.decathlon.onepay.controller;

import com.decathlon.onepay.dto.TransactionCreateDto;
import com.decathlon.onepay.dto.TransactionDto;
import com.decathlon.onepay.dto.TransactionUpdateDto;
import com.decathlon.onepay.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.decathlon.onepay.Util.Constant.API_TRANSACTION;

@RestController
@AllArgsConstructor
@RequestMapping(API_TRANSACTION)
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody final TransactionCreateDto transactionCreateDto) {
        TransactionDto createdTransaction = transactionService.create(transactionCreateDto);
        return ResponseEntity.created(URI.create(API_TRANSACTION + "/" + createdTransaction.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable final Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransactionById(@PathVariable final Long id, @RequestBody final TransactionUpdateDto transactionUpdateDto) {
        return ResponseEntity.ok(transactionService.update(id, transactionUpdateDto));
    }
}
