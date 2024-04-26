package com.decathlon.onepay.dto;

import com.decathlon.onepay.Util.TransactionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class TransactionDto extends TransactionCreateDto {

    private Long id;

    private BigDecimal amount;

    private TransactionStatus status;
}
