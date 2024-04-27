package com.decathlon.onepay.service;

import com.decathlon.onepay.Util.TransactionStatus;
import com.decathlon.onepay.dto.TransactionCreateDto;
import com.decathlon.onepay.dto.TransactionDto;
import com.decathlon.onepay.dto.TransactionUpdateDto;
import com.decathlon.onepay.entity.Transaction;
import com.decathlon.onepay.exception.TransactionNotFoundException;
import com.decathlon.onepay.exception.TransactionServiceException;
import com.decathlon.onepay.mapper.TransactionMapper;
import com.decathlon.onepay.repository.TransactionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.decathlon.onepay.Util.Constant.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private TransactionRepository repository;
    private TransactionMapper mapper;

    /**
     * Create a new transaction
     * @param transactionCreateDto
     * @return
     */
    public TransactionDto create(@NotNull TransactionCreateDto transactionCreateDto) {
        log.info("creating a new transaction");
        Transaction entity = mapper.toEntity(transactionCreateDto);
        entity.setStatus(TransactionStatus.NEW); // force the initial state
        if(!CollectionUtils.isEmpty(entity.getOrders())) {
            entity.getOrders().forEach(order -> {
                order.setTransaction(entity);
            });
        }
        return mapper.toDto(repository.save(entity));
    }

    /**
     * find all transactions
     * @return
     */
    public List<TransactionDto> getAll() {
        log.info("fetching all transactions");
        return mapper.toDto(repository.findAll());
    }

    /**
     * find a transaction by its id
     * @param id
     * @return
     */
    public TransactionDto getById(Long id) {
        log.info("fetching transaction with id={}", id);
        return mapper.toDto(findTransactionById(id));
    }

    /**
     * find a transaction by its id
     * @param id
     * @return
     */
    private Transaction findTransactionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error(TRANSACTION_NOT_FOUND + " : id = {}", id);
                    return new TransactionNotFoundException(TRANSACTION_NOT_FOUND);
                });
    }

    /**
     * Update a transaction
     * @param id
     * @param transactionUpdateDto
     * @return
     */
    public TransactionDto update(@NotNull Long id, @NotNull TransactionUpdateDto transactionUpdateDto) {
        log.info("updating transaction with id={}", id);
        Transaction entity = findTransactionById(id);
        // check business rules
        checkRules(transactionUpdateDto, entity);
        if(transactionUpdateDto.getStatus() != null) entity.setStatus(transactionUpdateDto.getStatus());
        if(transactionUpdateDto.getType() != null) entity.setType(transactionUpdateDto.getType());

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Check update business rules
     * @param dto
     * @param entity
     */
    private void checkRules(TransactionUpdateDto dto, Transaction entity) {
        if(TransactionStatus.CAPTURED.equals(entity.getStatus())) {
            log.error(NOT_UPDATABLE_TRANSACTION_LOG_MESSAGE + " id = {}", entity.getId());
            throw new TransactionServiceException(NOT_UPDATABLE_TRANSACTION);
        }
        if(TransactionStatus.CAPTURED.equals(dto.getStatus()) && !TransactionStatus.AUTHORIZED.equals(entity.getStatus())){
            log.error(INVALID_PAYMENT_TRANSACTION_STATUS_LOG_MESSAGE + " id = {}", entity.getId());
            throw new TransactionServiceException(INVALID_PAYMENT_TRANSACTION_STATUS);
        }
    }

}
