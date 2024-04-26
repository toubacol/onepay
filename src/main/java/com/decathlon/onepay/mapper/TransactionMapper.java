package com.decathlon.onepay.mapper;

import com.decathlon.onepay.dto.OrderCreateDto;
import com.decathlon.onepay.dto.TransactionCreateDto;
import com.decathlon.onepay.dto.TransactionDto;
import com.decathlon.onepay.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TransactionMapper {

    @Mapping(target = "amount", expression = "java(calculateTransactionAmount(transactionCreateDto))")
    public abstract Transaction toEntity(TransactionCreateDto transactionCreateDto);

    public abstract TransactionDto toDto(Transaction entity);

    public abstract TransactionCreateDto toCreateDto(Transaction entity);

    public abstract List<TransactionDto> toDto(List<Transaction> entityList);

    /**
     *
     * @param transactionCreateDto
     * @return
     */
    protected BigDecimal calculateTransactionAmount(TransactionCreateDto transactionCreateDto){
        if(transactionCreateDto != null && !CollectionUtils.isEmpty(transactionCreateDto.getOrders())){
            return transactionCreateDto.getOrders().stream()
                    .map(this::getOrderAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getOrderAmount(OrderCreateDto dto) {
        return (dto.getPrice() != null && dto.getQuantity() != null) ? dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())) : BigDecimal.ZERO;
    }
}
