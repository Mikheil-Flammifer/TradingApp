package com.my.tradingapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public record WatchlistRequest(

        @NotBlank(message = "Stock symbol is required")
        String symbol

) {}
