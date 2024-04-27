package com.decathlon.onepay.dto;

import com.decathlon.onepay.Util.PaymentTypeEnum;
import com.decathlon.onepay.Util.TransactionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class TransactionDto {

    private Long id;

    private BigDecimal amount;

    private TransactionStatus status;

    private PaymentTypeEnum type;

    private List<OrderDto> orders;
}
