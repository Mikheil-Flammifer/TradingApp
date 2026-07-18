package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.TransactionResponse;
import com.my.tradingapp.entity.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "stock.symbol", target = "symbol")
    @Mapping(source = "stock.companyName", target = "companyName")
    TransactionResponse toResponse(Transaction transaction);

    List<TransactionResponse> toResponseList(List<Transaction> transactions);
}

