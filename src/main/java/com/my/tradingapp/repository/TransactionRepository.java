package com.my.tradingapp.repository;

import com.my.tradingapp.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByPortfolioId(Long portfolioId);
    List<Transaction> findByPortfolioIdAndStockId(Long portfolioId, Long stockId);
}
