package com.my.tradingapp.repository;

import com.my.tradingapp.entity.holding.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    List<Holding> findByPortfolioId(Long portfolioId);

    // Find a specific stock holding in a portfolio
    Optional<Holding> findByPortfolioIdAndStockId(Long portfolioId, Long stockId);

    // Check if user holds a specific stock
    boolean existsByPortfolioIdAndStockId(Long portfolioId, Long stockId);

    // Delete holding when quantity reaches zero after a SELL
    void deleteByPortfolioIdAndStockId(Long portfolioId, Long stockId);
}
