package com.my.tradingapp.dto.response;

import com.my.tradingapp.entity.order.OrderSide;
import com.my.tradingapp.entity.order.OrderStatus;
import com.my.tradingapp.entity.order.OrderType;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(

        Long id,
        String symbol,
        String companyName,
        OrderSide side,           // BUY or SELL
        OrderType orderType,      // MARKET or LIMIT
        OrderStatus status,       // PENDING, FILLED, CANCELLED, REJECTED
        BigDecimal quantity,
        BigDecimal limitPrice,    // null for MARKET orders
        BigDecimal filledPrice,   // null until filled
        BigDecimal totalValue,    // null until filled
        Instant createdAt,
        Instant filledAt          // null until filled

) {}
