package com.my.tradingapp.dto.request;

import com.my.tradingapp.entity.order.OrderSide;
import com.my.tradingapp.entity.order.OrderType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PlaceOrderRequest(

        @NotBlank(message = "Stock symbol is required")
        String symbol,

        @NotNull(message = "Order side is required (BUY or SELL)")
        OrderSide side,

        @NotNull(message = "Order type is required (MARKET or LIMIT)")
        OrderType orderType,

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.00000001", message = "Quantity must be greater than zero")
        BigDecimal quantity,

        // Only required for LIMIT orders — null for MARKET orders
        @DecimalMin(value = "0.01", message = "Limit price must be greater than zero")
        BigDecimal limitPrice

) {}