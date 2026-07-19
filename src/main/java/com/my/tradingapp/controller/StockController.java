package com.my.tradingapp.controller;

import com.my.tradingapp.dto.response.ApiResponse;
import com.my.tradingapp.dto.response.PriceHistoryResponse;
import com.my.tradingapp.dto.response.StockResponse;
import com.my.tradingapp.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // Get all available stocks
    @GetMapping
    public ResponseEntity<ApiResponse<List<StockResponse>>> getAllStocks() {
        List<StockResponse> response = stockService.getAllStocks();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Get single stock by symbol e.g. GET /api/stocks/AAPL
    @GetMapping("/{symbol}")
    public ResponseEntity<ApiResponse<StockResponse>> getStockBySymbol(
            @PathVariable String symbol) {
        StockResponse response = stockService.getStockBySymbol(symbol.toUpperCase());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Search stocks by name or symbol e.g. GET /api/stocks/search?q=apple
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StockResponse>>> searchStocks(
            @RequestParam String q) {
        List<StockResponse> response = stockService.searchStocks(q);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Get full price history for charts e.g. GET /api/stocks/AAPL/history
    @GetMapping("/{symbol}/history")
    public ResponseEntity<ApiResponse<List<PriceHistoryResponse>>> getPriceHistory(
            @PathVariable String symbol) {
        List<PriceHistoryResponse> response = stockService.getPriceHistory(symbol.toUpperCase());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Get price history for a date range e.g. GET /api/stocks/AAPL/history?from=...&to=...
    @GetMapping("/{symbol}/history/range")
    public ResponseEntity<ApiResponse<List<PriceHistoryResponse>>> getPriceHistoryByRange(
            @PathVariable String symbol,
            @RequestParam String from,
            @RequestParam String to) {
        List<PriceHistoryResponse> response = stockService.getPriceHistoryByRange(
                symbol.toUpperCase(), from, to);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
