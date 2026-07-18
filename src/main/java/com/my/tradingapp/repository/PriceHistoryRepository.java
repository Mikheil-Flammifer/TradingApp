package com.my.tradingapp.repository;

import com.my.tradingapp.entity.pricehistory.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    // All history for a stock ordered oldest → newest (for charts)
    List<PriceHistory> findByStockIdOrderByTimestampAsc(Long stockId);

    // Filtered by date range for custom chart windows
    List<PriceHistory> findByStockIdAndTimestampBetweenOrderByTimestampAsc(
            Long stockId,
            Instant from,
            Instant to
    );

    // Latest snapshot for a stock
    PriceHistory findTopByStockIdOrderByTimestampDesc(Long stockId);
}
