package com.decathlon.onepay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderCreateDto {
    @NotBlank(message = "The product name is mandatory")
    private String productName;

    @NotNull(message = "The order quantity is mandatory")
    private Integer quantity;

    @NotNull(message = "The order price is mandatory")
    private BigDecimal price;

}
