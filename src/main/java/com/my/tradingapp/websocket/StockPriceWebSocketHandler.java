package com.my.tradingapp.websocket;

import com.my.tradingapp.dto.response.StockResponse;
import com.my.tradingapp.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StockPriceWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private final StockService stockService;

    // Client subscribes to: /topic/prices
    // Receives: all stock prices every 5 seconds
    @Scheduled(fixedDelay = 5000)
    public void pushAllPrices() {
        try {
            List<StockResponse> stocks = stockService.getAllStocks();
            // Push to all subscribers of /topic/prices
            messagingTemplate.convertAndSend("/topic/prices", stocks);
        } catch (Exception e) {
            log.error("Failed to push stock prices: {}", e.getMessage());
        }
    }

    // Client subscribes to: /topic/prices/AAPL
    // Client sends message to: /app/subscribe/AAPL
    // Receives: live price for that specific stock
    @MessageMapping("/subscribe/{symbol}")
    public void subscribeToStock(@DestinationVariable String symbol) {
        try {
            StockResponse stock = stockService.getStockBySymbol(symbol.toUpperCase());
            // Push to subscribers of /topic/prices/{symbol}
            messagingTemplate.convertAndSend("/topic/prices/" + symbol.toUpperCase(), stock);
            log.info("Client subscribed to stock: {}", symbol);
        } catch (Exception e) {
            log.error("Failed to push price for {}: {}", symbol, e.getMessage());
        }
    }

    // Push price update for a single stock
    // Called by StockService after refreshing prices
    public void pushStockUpdate(StockResponse stock) {
        messagingTemplate.convertAndSend(
                "/topic/prices/" + stock.symbol(), stock);
        log.info("Pushed price update for {}: {}", stock.symbol(), stock.currentPrice());
    }
}
