package com.springboot.order.mapper;

import com.springboot.order.Order;
import com.springboot.order.OrderPostDto;
import com.springboot.order.OrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderPostDtoToOrder(OrderPostDto orderPostDto);
    OrderResponseDto orderToOrderResponseDto(Order order);
}
