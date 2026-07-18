package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.response.PriceHistoryResponse;
import com.my.tradingapp.dto.response.StockResponse;
import com.my.tradingapp.mapper.PriceHistoryMapper;
import com.my.tradingapp.mapper.StockMapper;
import com.my.tradingapp.repository.PriceHistoryRepository;
import com.my.tradingapp.repository.StockRepository;
import com.my.tradingapp.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final StockMapper stockMapper;
    private final PriceHistoryMapper priceHistoryMapper;
    // ExternalMarketApiClient will be injected here once created

    @Override
    public List<StockResponse> getAllStocks() {
        // TODO:
        // 1. Fetch all stocks from DB
        // 2. Map to StockResponse list and return
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public StockResponse getStockBySymbol(String symbol) {
        // TODO:
        // 1. Find stock by symbol — throw NotFoundException if not found
        // 2. Map to StockResponse and return
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<StockResponse> searchStocks(String keyword) {
        // TODO:
        // 1. Search stocks where symbol or companyName contains keyword (case insensitive)
        // 2. Map to StockResponse list and return
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<PriceHistoryResponse> getPriceHistory(String symbol) {
        // TODO:
        // 1. Find stock by symbol
        // 2. Fetch all price history ordered by timestamp ASC
        // 3. Map to PriceHistoryResponse list and return
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<PriceHistoryResponse> getPriceHistoryByRange(String symbol, String from, String to) {
        // TODO:
        // 1. Parse from/to strings to Instant
        // 2. Find stock by symbol
        // 3. Fetch price history between from and to
        // 4. Map to PriceHistoryResponse list and return
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void refreshStockPrices() {
        // TODO:
        // 1. Fetch all stocks from DB
        // 2. For each stock call external market API to get latest price
        // 3. Update stock.currentPrice and stock.priceUpdatedAt
        // 4. Save a new PriceHistory snapshot
        // 5. Save updated stocks
        // Called by @Scheduled job every X minutes
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
