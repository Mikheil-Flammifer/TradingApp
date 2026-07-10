package com.my.tradingapp.entity.transaction;

import com.my.tradingapp.entity.order.Order;
import com.my.tradingapp.entity.order.OrderSide;
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
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The order this transaction originated from
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Enumerated(EnumType.STRING)
    @Column(name = "side", nullable = false)
    private OrderSide side;

    @Column(name = "quantity", nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    // Actual execution price
    @Column(name = "price", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    // Total value of this trade (quantity * price)
    @Column(name = "total_value", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalValue;

    // Realized profit/loss — only relevant for SELL transactions
    @Column(name = "realized_pnl", precision = 19, scale = 4)
    private BigDecimal realizedPnl;

    @Column(name = "executed_at", nullable = false, updatable = false)
    private Instant executedAt;

    @PrePersist
    public void prePersist() {
        this.executedAt = Instant.now();
    }
}