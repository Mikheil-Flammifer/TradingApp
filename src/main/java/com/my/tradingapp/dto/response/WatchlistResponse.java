package com.my.tradingapp.dto.response;

import java.time.Instant;
import java.util.List;

public record WatchlistResponse(

        Long id,
        Long userId,
        List<StockResponse> stocks,
        Instant createdAt

) {}