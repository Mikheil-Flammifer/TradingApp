package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.HoldingResponse;
import com.my.tradingapp.entity.holding.Holding;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HoldingMapper {

    // currentPrice, currentValue, unrealizedPnl, unrealizedPnlPct
    // are calculated fields — they are passed in explicitly by the service layer
    // using the toResponse(holding, currentPrice) helper below
    @Mapping(source = "holding.stock.symbol", target = "symbol")
    @Mapping(source = "holding.stock.companyName", target = "companyName")
    @Mapping(source = "currentPrice", target = "currentPrice")
    @Mapping(source = "currentValue", target = "currentValue")
    @Mapping(source = "unrealizedPnl", target = "unrealizedPnl")
    @Mapping(source = "unrealizedPnlPct", target = "unrealizedPnlPct")
    HoldingResponse toResponse(
            Holding holding,
            BigDecimal currentPrice,
            BigDecimal currentValue,
            BigDecimal unrealizedPnl,
            BigDecimal unrealizedPnlPct
    );

    List<HoldingResponse> toResponseList(List<Holding> holdings);
}
