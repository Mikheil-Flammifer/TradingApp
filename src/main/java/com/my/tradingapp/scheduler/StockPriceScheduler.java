package com.my.tradingapp.scheduler;

import com.my.tradingapp.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockPriceScheduler {

    private final StockService stockService;

    // Refresh stock prices every 5 minutes during market hours
    // Cron: second minute hour day month weekday
    // This runs every 5 minutes Monday-Friday
    @Scheduled(cron = "0 */5 * * * MON-FRI")
    public void refreshStockPrices() {
        log.info("Starting stock price refresh...");
        try {
            stockService.refreshStockPrices();
            log.info("Stock price refresh completed successfully");
        } catch (Exception e) {
            log.error("Stock price refresh failed: {}", e.getMessage(), e);
        }
    }

    // Save a price history snapshot every 15 minutes for chart data
    @Scheduled(cron = "0 */15 * * * MON-FRI")
    public void savePriceHistorySnapshot() {
        log.info("Saving price history snapshot...");
        try {
            // TODO: call stockService.savePriceSnapshot() once implemented
            log.info("Price history snapshot saved");
        } catch (Exception e) {
            log.error("Price history snapshot failed: {}", e.getMessage(), e);
        }
    }
}
