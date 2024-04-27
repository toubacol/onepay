package com.decathlon.onepay.mapper;

import com.decathlon.onepay.dto.OrderCreateDto;
import com.decathlon.onepay.dto.OrderDto;
import com.decathlon.onepay.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderCreateDto orderCreateDto);

    OrderDto toDto(Order entity);

    List<Order> toEntity(List<OrderCreateDto> orderCreateDtoList);

    List<OrderDto> toDto(List<Order> entity);
}
