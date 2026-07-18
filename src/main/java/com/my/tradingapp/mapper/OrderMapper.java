package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.OrderResponse;
import com.my.tradingapp.entity.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "stock.symbol", target = "symbol")
    @Mapping(source = "stock.companyName", target = "companyName")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);
}
