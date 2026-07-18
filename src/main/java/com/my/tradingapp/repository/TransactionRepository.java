package com.my.tradingapp.repository;

import com.my.tradingapp.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByPortfolioIdOrderByExecutedAtDesc(Long portfolioId);

    List<Transaction> findByPortfolioIdAndStockIdOrderByExecutedAtDesc(Long portfolioId, Long stockId);

    Optional<Transaction> findByIdAndPortfolioId(Long id, Long portfolioId);

    // Used for realized P&L summary per stock
    List<Transaction> findByPortfolioIdAndStockSymbol(Long portfolioId, String symbol);
}
