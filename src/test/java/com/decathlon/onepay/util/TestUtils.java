package com.decathlon.onepay.util;

import com.decathlon.onepay.Util.PaymentTypeEnum;
import com.decathlon.onepay.Util.TransactionStatus;
import com.decathlon.onepay.dto.*;
import com.decathlon.onepay.entity.Order;
import com.decathlon.onepay.entity.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    /**
     *
     * @return
     */
    public static TransactionUpdateDto getTransactionUpdateDto() {
        TransactionUpdateDto dto = new TransactionUpdateDto();
        dto.setType(PaymentTypeEnum.GIFT_CARD);
        dto.setStatus(TransactionStatus.AUTHORIZED);
        return dto;
    }

    /**
     *
     * @return
     */
    public static TransactionCreateDto getTransactionCreateDto() {
        TransactionCreateDto dto = new TransactionCreateDto();
        dto.setType(PaymentTypeEnum.GIFT_CARD);

        OrderCreateDto order = new OrderCreateDto();
        order.setPrice(BigDecimal.valueOf(14.80));
        order.setQuantity(1);
        order.setProductName("Cap");
        dto.setOrders(new ArrayList<>(Arrays.asList(order)));
        return dto;
    }


    /**
     *
     * @return
     */
    public static List<TransactionDto> getValidTranctionDtoList() {
        // first transactions
        TransactionDto transaction1 = getTransactionDto_1();
        // second transactions
        TransactionDto transaction2 = getTransactionDto_2();

        return List.of(transaction1, transaction2);
    }

    /**
     *
     * @return
     */
    public static List<Transaction> getValidTranctionList() {
        // first transactions
        Transaction transaction1 = getTransaction_1();
        // second transactions
        Transaction transaction2 = getTransaction_2();

        return List.of(transaction1, transaction2);
    }

    /**
     *
     * @return
     */
    public static TransactionDto getTransactionDto_2() {
        TransactionDto transaction2 = new TransactionDto();
        transaction2.setId(2L);
        transaction2.setStatus(TransactionStatus.NEW);
        transaction2.setType(PaymentTypeEnum.PAYPAL);
        OrderDto orderBike = new OrderDto();
        orderBike.setId(3L);
        orderBike.setPrice(BigDecimal.valueOf(208));
        orderBike.setQuantity(1);
        orderBike.setProductName("Bike");
        transaction2.setOrders(List.of(orderBike));
        return transaction2;
    }

    /**
     *
     * @return
     */
    public static TransactionDto getTransactionDto_1() {
        TransactionDto transaction1 = new TransactionDto();
        transaction1.setId(1L);
        transaction1.setStatus(TransactionStatus.NEW);
        transaction1.setType(PaymentTypeEnum.CREDIT_CARD);

        OrderDto glovesOrder = new OrderDto();
        glovesOrder.setId(1L);
        glovesOrder.setPrice(BigDecimal.valueOf(10));
        glovesOrder.setQuantity(4);
        glovesOrder.setProductName("Gloves");

        OrderDto capOrder = new OrderDto();
        capOrder.setId(2L);
        capOrder.setPrice(BigDecimal.valueOf(14.80));
        capOrder.setQuantity(1);
        capOrder.setProductName("Cap");

        transaction1.setOrders(List.of(capOrder, capOrder));
        return transaction1;
    }


    /**
     *
     * @return
     */
    public static Transaction getTransaction_2() {
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setStatus(TransactionStatus.NEW);
        transaction2.setType(PaymentTypeEnum.PAYPAL);
        transaction2.setAmount(BigDecimal.valueOf(208));
        Order orderBike = new Order();
        orderBike.setId(3L);
        orderBike.setPrice(BigDecimal.valueOf(208));
        orderBike.setQuantity(1);
        orderBike.setProductName("Bike");
        transaction2.setOrders(List.of(orderBike));
        return transaction2;
    }
    /**
     *
     * @return
     */
    public static Transaction getTransaction_1() {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setStatus(TransactionStatus.NEW);
        transaction1.setType(PaymentTypeEnum.CREDIT_CARD);
        transaction1.setAmount(BigDecimal.valueOf(54.80));

        Order glovesOrder = new Order();
        glovesOrder.setId(1L);
        glovesOrder.setPrice(BigDecimal.valueOf(10));
        glovesOrder.setQuantity(4);
        glovesOrder.setProductName("Gloves");

        Order capOrder = new Order();
        capOrder.setId(2L);
        capOrder.setPrice(BigDecimal.valueOf(14.80));
        capOrder.setQuantity(1);
        capOrder.setProductName("Cap");

        transaction1.setOrders(List.of(capOrder, capOrder));
        return transaction1;
    }

    public static OrderCreateDto getGlovesOrderCreateDto() {
        OrderCreateDto glovesOrder = new OrderCreateDto();
        glovesOrder.setPrice(BigDecimal.valueOf(10));
        glovesOrder.setQuantity(4);
        glovesOrder.setProductName("Gloves");
        return glovesOrder;
    }
}
