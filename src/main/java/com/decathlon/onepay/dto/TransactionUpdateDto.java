package com.decathlon.onepay.dto;

import com.decathlon.onepay.Util.PaymentTypeEnum;
import com.decathlon.onepay.Util.TransactionStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionUpdateDto {
    private PaymentTypeEnum type;
    private TransactionStatus status;
}
