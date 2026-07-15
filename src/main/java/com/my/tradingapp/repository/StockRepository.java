package com.my.tradingapp.repository;

import com.my.tradingapp.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySymbol(String symbol);
    List<Stock> findByExchange(String exchange);
    List<Stock> findBySector(String sector);
}