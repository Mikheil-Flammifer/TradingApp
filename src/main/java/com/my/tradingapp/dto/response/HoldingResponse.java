package com.my.tradingapp.dto.response;

import java.math.BigDecimal;

public record HoldingResponse(

        Long id,
        String symbol,
        String companyName,
        BigDecimal quantity,
        BigDecimal avgBuyPrice,
        BigDecimal totalCost,

        // calculated fields — filled in by service layer using current price
        BigDecimal currentPrice,
        BigDecimal currentValue,       // quantity * currentPrice
        BigDecimal unrealizedPnl,      // currentValue - totalCost
        BigDecimal unrealizedPnlPct    // unrealizedPnl / totalCost * 100

) {}
