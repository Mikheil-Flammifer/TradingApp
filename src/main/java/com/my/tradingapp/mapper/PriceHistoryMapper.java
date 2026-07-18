package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.PriceHistoryResponse;
import com.my.tradingapp.entity.pricehistory.PriceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceHistoryMapper {

    @Mapping(source = "stock.symbol", target = "symbol")
    PriceHistoryResponse toResponse(PriceHistory priceHistory);

    List<PriceHistoryResponse> toResponseList(List<PriceHistory> priceHistories);
}