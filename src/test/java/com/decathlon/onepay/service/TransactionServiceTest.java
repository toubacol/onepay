package com.decathlon.onepay.service;

import com.decathlon.onepay.Util.PaymentTypeEnum;
import com.decathlon.onepay.Util.TransactionStatus;
import com.decathlon.onepay.dto.OrderDto;
import com.decathlon.onepay.dto.TransactionCreateDto;
import com.decathlon.onepay.dto.TransactionDto;
import com.decathlon.onepay.dto.TransactionUpdateDto;
import com.decathlon.onepay.entity.Order;
import com.decathlon.onepay.entity.Transaction;
import com.decathlon.onepay.exception.TransactionNotFoundException;
import com.decathlon.onepay.exception.TransactionServiceException;
import com.decathlon.onepay.repository.TransactionRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.decathlon.onepay.Util.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static com.decathlon.onepay.util.TestUtils.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

    @MockBean
    private TransactionRepository repository;

    @Autowired
    private TransactionService service;

    @Test
    public void testGetAllTransactionsShouldSucceed() {

        List<Transaction> mockedTransactions = getValidTranctionList();
        when(repository.findAll()).thenReturn(mockedTransactions);
        List<TransactionDto> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertFalse(result.get(0).getOrders().isEmpty());
        assertFalse(result.get(1).getOrders().isEmpty());
    }

    @Test
    public void testGetTransactionByIdShouldSucceed() {

        Transaction mockedTransaction = getTransaction_2();
        when(repository.findById(anyLong())).thenReturn(Optional.of(mockedTransaction));
        TransactionDto result = service.getById(1L);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(PaymentTypeEnum.PAYPAL, result.getType());
        assertEquals(TransactionStatus.NEW, result.getStatus());
        assertEquals(BigDecimal.valueOf(208), result.getAmount());
        assertEquals(1, result.getOrders().size());
        OrderDto order = result.getOrders().get(0);
        assertEquals("Bike", order.getProductName());
        assertEquals(BigDecimal.valueOf(208), order.getPrice());
        assertEquals(1, order.getQuantity());

    }

    @Test
    public void testGetTransactionByIdNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        TransactionNotFoundException exception = assertThrows((TransactionNotFoundException.class), () -> service.getById(1L));
        assertEquals(TRANSACTION_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testCreateTransactionShouldSucceed() {
        Transaction mockedTransaction = getTransaction_2();
        when(repository.save(any(Transaction.class))).thenReturn(mockedTransaction);
        TransactionCreateDto transactionCreateDto = getTransactionCreateDto();
        TransactionDto result = service.create(transactionCreateDto);

        assertEquals(mockedTransaction.getId(), result.getId());
        assertEquals(mockedTransaction.getType(), result.getType());
        assertEquals(mockedTransaction.getStatus(), result.getStatus());
        assertEquals(mockedTransaction.getAmount(), result.getAmount());
        Order mockedOrder = mockedTransaction.getOrders().get(0);
        OrderDto createdOrder = result.getOrders().get(0);
        assertEquals(mockedOrder.getId(), createdOrder.getId());
        assertEquals(mockedOrder.getQuantity(), createdOrder.getQuantity());
        assertEquals(mockedOrder.getProductName(), createdOrder.getProductName());
        assertEquals(mockedOrder.getPrice(), createdOrder.getPrice());
    }

    @Test
    public void testUpdateTransactionShouldSucceed() {
        Transaction existingTransaction = getTransaction_2();
        when(repository.findById(anyLong())).thenReturn(Optional.of(existingTransaction));
        TransactionUpdateDto transactionUpdateDto = getTransactionUpdateDto();
        TransactionDto result = service.update(1L, transactionUpdateDto);

        ArgumentCaptor<Transaction> argument = ArgumentCaptor.forClass(Transaction.class);
        verify(repository, times(1)).save(argument.capture());
        Transaction value = argument.getValue();
        assertEquals(PaymentTypeEnum.GIFT_CARD, value.getType());
        assertEquals(TransactionStatus.AUTHORIZED, value.getStatus());
    }

    @Test
    public void testUpdateTransactionNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        TransactionNotFoundException exception = assertThrows((TransactionNotFoundException.class), () -> service.update(1L, new TransactionUpdateDto()));
        assertEquals(TRANSACTION_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testUpdateCapturedTransaction() {
        Transaction capturedTransaction = getTransaction_2();
        capturedTransaction.setStatus(TransactionStatus.CAPTURED);
        when(repository.findById(anyLong())).thenReturn(Optional.of(capturedTransaction));
        TransactionServiceException exception = assertThrows((TransactionServiceException.class), () -> service.update(1L, new TransactionUpdateDto()));
        assertEquals(NOT_UPDATABLE_TRANSACTION, exception.getMessage());
    }

    @Test
    public void testUpdateTransactionFromNewToCaptured() {
        Transaction newTransaction = getTransaction_2();
        when(repository.findById(anyLong())).thenReturn(Optional.of(newTransaction));
        @NotNull TransactionUpdateDto capturedTransactionDto = getTransactionUpdateDto();
        capturedTransactionDto.setStatus(TransactionStatus.CAPTURED);
        TransactionServiceException exception = assertThrows((TransactionServiceException.class), () -> service.update(1L, capturedTransactionDto));
        assertEquals(INVALID_PAYMENT_TRANSACTION_STATUS, exception.getMessage());
    }
}
