package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.request.WatchlistRequest;
import com.my.tradingapp.dto.response.WatchlistResponse;
import com.my.tradingapp.entity.stock.Stock;
import com.my.tradingapp.entity.user.User;
import com.my.tradingapp.entity.watchlist.Watchlist;
import com.my.tradingapp.exception.AppException;
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
        Watchlist watchlist = findByUserId(userId);
        return watchlistMapper.toResponse(watchlist);
    }

    @Override
    @Transactional
    public WatchlistResponse addStock(Long userId, WatchlistRequest request) {
        Watchlist watchlist = findByUserId(userId);
        Stock stock = stockRepository.findBySymbol(request.symbol().toUpperCase())
                .orElseThrow(() -> AppException.notFound("Stock not found: " + request.symbol()));

        boolean alreadyAdded = watchlist.getStocks().stream()
                .anyMatch(s -> s.getSymbol().equals(stock.getSymbol()));
        if (alreadyAdded)
            throw AppException.conflict("Stock already in watchlist: " + stock.getSymbol());

        watchlist.getStocks().add(stock);
        watchlistRepository.save(watchlist);
        return watchlistMapper.toResponse(watchlist);
    }

    @Override
    @Transactional
    public WatchlistResponse removeStock(Long userId, String symbol) {
        Watchlist watchlist = findByUserId(userId);
        boolean removed = watchlist.getStocks()
                .removeIf(s -> s.getSymbol().equals(symbol.toUpperCase()));

        if (!removed)
            throw AppException.notFound("Stock not found in watchlist: " + symbol);

        watchlistRepository.save(watchlist);
        return watchlistMapper.toResponse(watchlist);
    }

    @Override
    @Transactional
    public void createWatchlistForUser(User user) {
        Watchlist watchlist = Watchlist.builder()
                .user(user)
                .build();
        watchlistRepository.save(watchlist);
    }

    private Watchlist findByUserId(Long userId) {
        return watchlistRepository.findByUserId(userId)
                .orElseThrow(() -> AppException.notFound("Watchlist not found for user: " + userId));
    }
}