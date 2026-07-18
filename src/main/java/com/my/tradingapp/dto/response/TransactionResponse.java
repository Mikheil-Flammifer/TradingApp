package com.my.tradingapp.dto.response;

import com.my.tradingapp.entity.order.OrderSide;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(

        Long id,
        Long orderId,
        String symbol,
        String companyName,
        OrderSide side,           // BUY or SELL
        BigDecimal quantity,
        BigDecimal price,         // actual execution price
        BigDecimal totalValue,    // quantity * price
        BigDecimal realizedPnl,   // only meaningful for SELL — null for BUY
        Instant executedAt

) {}
