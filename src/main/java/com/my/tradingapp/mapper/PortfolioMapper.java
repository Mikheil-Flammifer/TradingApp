package com.my.tradingapp.mapper;

import com.my.tradingapp.dto.response.HoldingResponse;
import com.my.tradingapp.dto.response.PortfolioResponse;
import com.my.tradingapp.entity.portfolio.Portfolio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {

    // totalHoldingsValue, totalPortfolioValue, totalPnl, totalPnlPct
    // are calculated in the service layer and passed in explicitly
    @Mapping(source = "portfolio.user.id", target = "userId")
    @Mapping(source = "portfolio.user.userName", target = "username")
    @Mapping(source = "totalHoldingsValue", target = "totalHoldingsValue")
    @Mapping(source = "totalPortfolioValue", target = "totalPortfolioValue")
    @Mapping(source = "totalPnl", target = "totalPnl")
    @Mapping(source = "totalPnlPct", target = "totalPnlPct")
    @Mapping(source = "holdings", target = "holdings")
    PortfolioResponse toResponse(
            Portfolio portfolio,
            BigDecimal totalHoldingsValue,
            BigDecimal totalPortfolioValue,
            BigDecimal totalPnl,
            BigDecimal totalPnlPct,
            List<HoldingResponse> holdings
    );
}
