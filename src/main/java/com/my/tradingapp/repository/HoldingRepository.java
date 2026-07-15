package com.my.tradingapp.repository;

import com.my.tradingapp.entity.holding.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByPortfolioId(Long portfolioId);
    Optional<Holding> findByPortfolioIdAndStockId(Long portfolioId, Long stockId);
}