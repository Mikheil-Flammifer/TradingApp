
package com.my.tradingapp.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record StockResponse(

        Long id,
        String symbol,
        String companyName,
        String exchange,
        String sector,
        BigDecimal currentPrice,
        Instant priceUpdatedAt

) {}