package com.decathlon.onepay.dto;

import com.decathlon.onepay.Util.PaymentTypeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class TransactionCreateDto {

    @NotNull(message = "The payment type is mandatory")
    private PaymentTypeEnum type;

    @Valid
    private List<OrderCreateDto> orders;
}
