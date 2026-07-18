package com.my.tradingapp.watchlist;

import com.my.tradingapp.entity.stock.Stock;
import com.my.tradingapp.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "watchlists")
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Many-to-many: a watchlist has many stocks; a stock can be on many watchlists
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "watchlist_stocks",                          // join table name
            joinColumns = @JoinColumn(name = "watchlist_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id")
    )
    @Builder.Default
    private List<Stock> stocks = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}