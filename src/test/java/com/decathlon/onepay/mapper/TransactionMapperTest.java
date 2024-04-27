package com.decathlon.onepay.mapper;

import com.decathlon.onepay.Util.PaymentTypeEnum;
import com.decathlon.onepay.dto.TransactionCreateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.decathlon.onepay.util.TestUtils.getGlovesOrderCreateDto;
import static com.decathlon.onepay.util.TestUtils.getTransactionCreateDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TransactionMapperImpl.class})
public class TransactionMapperTest {

    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    public void testCalculateTransactionAmountWithValidOrders() {
        TransactionCreateDto transactionCreateDto = getTransactionCreateDto();
        transactionCreateDto.getOrders().add(getGlovesOrderCreateDto());

        BigDecimal result = transactionMapper.calculateTransactionAmount(transactionCreateDto);

        assertEquals(BigDecimal.valueOf(54.80), result);
    }

    @Test
    public void testCalculateTransactionAmountWithEmptyOrderListShouldReturnZero() {
        TransactionCreateDto transactionWithoutOrders = new TransactionCreateDto();
        transactionWithoutOrders.setType(PaymentTypeEnum.GIFT_CARD);
        transactionWithoutOrders.setOrders(List.of());

        assertEquals(BigDecimal.ZERO, transactionMapper.calculateTransactionAmount(transactionWithoutOrders));
    }

    @Test
    public void testCalculateTransactionAmountWithNullParamShouldReturnZero() {
        assertEquals(BigDecimal.ZERO, transactionMapper.calculateTransactionAmount(null));
    }
}
