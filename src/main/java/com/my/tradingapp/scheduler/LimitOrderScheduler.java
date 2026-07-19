package com.my.tradingapp.scheduler;

import com.my.tradingapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LimitOrderScheduler {

    private final OrderService orderService;

    // Check and process pending LIMIT orders every minute during market hours
    // If current price meets the limit condition → fill the order automatically
    @Scheduled(cron = "0 * * * * MON-FRI")
    public void processPendingLimitOrders() {
        log.info("Processing pending limit orders...");
        try {
            orderService.processPendingLimitOrders();
            log.info("Limit order processing completed");
        } catch (Exception e) {
            log.error("Limit order processing failed: {}", e.getMessage(), e);
        }
    }
}
