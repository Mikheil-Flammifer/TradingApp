package com.my.tradingapp.entity.portfolio;

import com.my.tradingapp.entity.holding.Holding;
import com.my.tradingapp.entity.order.Order;
import com.my.tradingapp.entity.user.User;
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
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each portfolio belongs to exactly one user
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Available cash the user can spend on trades
    @Column(name = "cash_balance", nullable = false, precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal cashBalance = BigDecimal.valueOf(10000.00); // default starting balance

    // Total deposited across all time (for P&L calc)
    @Column(name = "total_deposited", nullable = false, precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal totalDeposited = BigDecimal.valueOf(10000.00);

    // What the user currently holds
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Holding> holdings = new ArrayList<>();

    // Full order history
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

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