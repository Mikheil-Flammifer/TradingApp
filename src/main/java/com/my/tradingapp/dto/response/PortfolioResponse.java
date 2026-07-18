package com.my.tradingapp.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PortfolioResponse(

        Long id,
        Long userId,
        String username,

        // Cash available to trade
        BigDecimal cashBalance,

        // Total ever deposited
        BigDecimal totalDeposited,

        // Sum of all holdings at current prices
        BigDecimal totalHoldingsValue,

        // cashBalance + totalHoldingsValue
        BigDecimal totalPortfolioValue,

        // totalPortfolioValue - totalDeposited
        BigDecimal totalPnl,

        // totalPnl / totalDeposited * 100
        BigDecimal totalPnlPct,

        List<HoldingResponse> holdings,

        Instant createdAt

) {}
