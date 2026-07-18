package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.request.WatchlistRequest;
import com.my.tradingapp.dto.response.WatchlistResponse;
import com.my.tradingapp.entity.user.User;
import com.my.tradingapp.mapper.WatchlistMapper;
import com.my.tradingapp.repository.StockRepository;
import com.my.tradingapp.repository.WatchlistRepository;
import com.my.tradingapp.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final StockRepository stockRepository;
    private final WatchlistMapper watchlistMapper;

    @Override
    public WatchlistResponse getWatchlist(Long userId) {
        // TODO:
        // 1. Find watchlist by userId — throw NotFoundException if not found
        // 2. Map and return WatchlistResponse (includes current prices via StockMapper)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public WatchlistResponse addStock(Long userId, WatchlistRequest request) {
        // TODO:
        // 1. Find watchlist by userId
        // 2. Find stock by symbol — throw NotFoundException if not found
        // 3. Check stock not already in watchlist
        // 4. Add stock to watchlist.stocks
        // 5. Save and return updated WatchlistResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public WatchlistResponse removeStock(Long userId, String symbol) {
        // TODO:
        // 1. Find watchlist by userId
        // 2. Find stock by symbol
        // 3. Remove stock from watchlist.stocks
        // 4. Save and return updated WatchlistResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void createWatchlistForUser(User user) {
        // TODO:
        // 1. Create new empty Watchlist
        // 2. Set user
        // 3. Save watchlist
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
