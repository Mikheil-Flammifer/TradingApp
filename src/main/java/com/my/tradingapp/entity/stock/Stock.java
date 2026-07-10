package com.my.tradingapp.entity.stock;

import com.my.tradingapp.entity.pricehistory.PriceHistory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ticker symbol e.g. "AAPL", "TSLA"
    @Column(name = "symbol", nullable = false, unique = true, length = 10)
    private String symbol;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "exchange", nullable = false, length = 20)
    private String exchange;  // e.g. "NASDAQ", "NYSE"

    @Column(name = "sector")
    private String sector;    // e.g. "Technology", "Finance"

    // Most recent price — refreshed by scheduled job
    @Column(name = "current_price", precision = 19, scale = 4)
    private BigDecimal currentPrice;

    @Column(name = "price_updated_at")
    private Instant priceUpdatedAt;

    // All historical price  for this stock
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PriceHistory> priceHistory = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}