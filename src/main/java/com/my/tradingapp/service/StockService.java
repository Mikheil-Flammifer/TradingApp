package com.my.tradingapp.service;

import com.my.tradingapp.dto.response.PriceHistoryResponse;
import com.my.tradingapp.dto.response.StockResponse;

import java.util.List;

public interface StockService {

    // Get all available stocks
    List<StockResponse> getAllStocks();

    // Get single stock by symbol e.g. "AAPL"
    StockResponse getStockBySymbol(String symbol);

    // Search stocks by name or symbol keyword
    List<StockResponse> searchStocks(String keyword);

    // Get price history for charts — optionally filtered by range
    List<PriceHistoryResponse> getPriceHistory(String symbol);

    List<PriceHistoryResponse> getPriceHistoryByRange(String symbol, String from, String to);

    // Called by scheduled job to refresh prices from external API
    void refreshStockPrices();
}
