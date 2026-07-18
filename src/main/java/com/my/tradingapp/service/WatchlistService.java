package com.my.tradingapp.service;

import com.my.tradingapp.dto.request.WatchlistRequest;
import com.my.tradingapp.dto.response.WatchlistResponse;

public interface WatchlistService {

    // Get user's watchlist with current prices
    WatchlistResponse getWatchlist(Long userId);

    // Add a stock to watchlist by symbol
    WatchlistResponse addStock(Long userId, WatchlistRequest request);

    // Remove a stock from watchlist by symbol
    WatchlistResponse removeStock(Long userId, String symbol);

    // Called internally on register — creates empty watchlist for new user
    void createWatchlistForUser(com.my.tradingapp.entity.user.User user);
}
