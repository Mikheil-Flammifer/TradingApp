package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.StockResponse;
import com.my.tradingapp.entity.stock.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockResponse toResponse(Stock stock);

    List<StockResponse> toResponseList(List<Stock> stocks);
}
