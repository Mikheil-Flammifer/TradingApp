package com.my.tradingapp.entity.pricehistory;

import com.my.tradingapp.entity.stock.Stock;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "price_history",
        indexes = {
                @Index(name = "idx_price_history_stock_timestamp", columnList = "stock_id, timestamp")
                // Index on stock + time — heavily queried for chart data
        }
)
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "open_price", precision = 19, scale = 4)
    private BigDecimal openPrice;

    @Column(name = "close_price", precision = 19, scale = 4)
    private BigDecimal closePrice;

    @Column(name = "high_price", precision = 19, scale = 4)
    private BigDecimal highPrice;

    @Column(name = "low_price", precision = 19, scale = 4)
    private BigDecimal lowPrice;

    @Column(name = "volume")
    private Long volume;

    // When this snapshot was recorded
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;
}