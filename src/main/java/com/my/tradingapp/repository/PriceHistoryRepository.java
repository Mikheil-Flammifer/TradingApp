package com.my.tradingapp.repository;

import com.my.tradingapp.entity.pricehistory.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    List<PriceHistory> findByStockIdOrderByTimestampAsc(Long stockId);
    List<PriceHistory> findByStockIdAndTimestampBetween(Long stockId, Instant from, Instant to);
}
