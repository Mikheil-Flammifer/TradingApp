package com.my.tradingapp.repository;

import com.my.tradingapp.entity.watchlist.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    Optional<Watchlist> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
