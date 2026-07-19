package com.my.tradingapp.service.impl;

import com.my.tradingapp.client.MarketDataClient;
import com.my.tradingapp.dto.response.PriceHistoryResponse;
import com.my.tradingapp.dto.response.StockResponse;
import com.my.tradingapp.entity.stock.Stock;
import com.my.tradingapp.entity.pricehistory.PriceHistory;
import com.my.tradingapp.exception.AppException;
import com.my.tradingapp.mapper.PriceHistoryMapper;
import com.my.tradingapp.mapper.StockMapper;
import com.my.tradingapp.repository.PriceHistoryRepository;
import com.my.tradingapp.repository.StockRepository;
import com.my.tradingapp.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final StockMapper stockMapper;
    private final PriceHistoryMapper priceHistoryMapper;
    private final MarketDataClient marketDataClient;

    @Override
    public List<StockResponse> getAllStocks() {
        return stockMapper.toResponseList(stockRepository.findAll());
    }

    @Override
    public StockResponse getStockBySymbol(String symbol) {
        return stockMapper.toResponse(findBySymbol(symbol));
    }

    @Override
    public List<StockResponse> searchStocks(String keyword) {
        return stockMapper.toResponseList(stockRepository.searchByKeyword(keyword));
    }

    @Override
    public List<PriceHistoryResponse> getPriceHistory(String symbol) {
        Stock stock = findBySymbol(symbol);
        return priceHistoryMapper.toResponseList(
                priceHistoryRepository.findByStockIdOrderByTimestampAsc(stock.getId()));
    }

    @Override
    public List<PriceHistoryResponse> getPriceHistoryByRange(
            String symbol, String from, String to) {
        Stock stock = findBySymbol(symbol);
        return priceHistoryMapper.toResponseList(
                priceHistoryRepository.findByStockIdAndTimestampBetweenOrderByTimestampAsc(
                        stock.getId(), Instant.parse(from), Instant.parse(to)));
    }

    @Override
    @Transactional
    public void refreshStockPrices() {
        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            try {
                BigDecimal price = marketDataClient.fetchCurrentPrice(stock.getSymbol());
                if (price != null) {
                    stock.setCurrentPrice(price);
                    stock.setPriceUpdatedAt(Instant.now());
                    stockRepository.save(stock);

                    // Save price history snapshot for charts
                    PriceHistory snapshot = PriceHistory.builder()
                            .stock(stock)
                            .closePrice(price)
                            .timestamp(Instant.now())
                            .build();
                    priceHistoryRepository.save(snapshot);

                    log.info("Updated price for {}: {}", stock.getSymbol(), price);
                }
            } catch (Exception e) {
                log.error("Failed to update price for {}: {}", stock.getSymbol(), e.getMessage());
            }
        }
    }

    // Used internally by OrderService and other services
    public Stock findBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol.toUpperCase())
                .orElseThrow(() -> AppException.notFound("Stock not found: " + symbol));
    }
}