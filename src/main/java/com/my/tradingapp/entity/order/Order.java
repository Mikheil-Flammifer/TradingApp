package com.my.tradingapp.entity.order;

import com.my.tradingapp.entity.portfolio.Portfolio;
import com.my.tradingapp.entity.stock.Stock;
import com.my.tradingapp.entity.transaction.Transaction;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    // BUY or SELL
    @Enumerated(EnumType.STRING)
    @Column(name = "side", nullable = false)
    private OrderSide side;

    // MARKET or LIMIT
    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    // PENDING, FILLED, CANCELLED, REJECTED
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    // Number of shares requested
    @Column(name = "quantity", nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    // For LIMIT orders — the target price; null for MARKET orders
    @Column(name = "limit_price", precision = 19, scale = 4)
    private BigDecimal limitPrice;

    // Price at which the order was actually filled; set when status → FILLED
    @Column(name = "filled_price", precision = 19, scale = 4)
    private BigDecimal filledPrice;

    // Total value of the trade once filled (filledPrice * quantity)
    @Column(name = "total_value", precision = 19, scale = 4)
    private BigDecimal totalValue;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "filled_at")
    private Instant filledAt;

    // Linked transaction created when this order is filled
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Transaction transaction;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}