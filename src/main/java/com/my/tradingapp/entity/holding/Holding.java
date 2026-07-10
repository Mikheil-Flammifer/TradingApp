package com.my.tradingapp.entity.holding;

import com.my.tradingapp.entity.portfolio.Portfolio;
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
        name = "holdings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"portfolio_id", "stock_id"})
)
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    // How many shares the user owns
    @Column(name = "quantity", nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    // Average price paid per share (updated on each BUY)
    @Column(name = "avg_buy_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal avgBuyPrice;

    // Total cost basis (quantity * avgBuyPrice — for P&L)
    @Column(name = "total_cost", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalCost;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}