package com.my.tradingapp.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceHistoryResponse(

        String symbol,
        BigDecimal openPrice,
        BigDecimal closePrice,
        BigDecimal highPrice,
        BigDecimal lowPrice,
        Long volume,
        Instant timestamp

) {}
