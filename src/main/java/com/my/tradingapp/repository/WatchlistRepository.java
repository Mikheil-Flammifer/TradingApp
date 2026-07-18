package com.my.tradingapp.repository;

import com.my.tradingapp.entity.watchlist.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    Optional<Watchlist> findByUserId(Long userId);
}