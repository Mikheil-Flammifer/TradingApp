package com.my.tradingapp.repository;

import com.my.tradingapp.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findBySymbol(String symbol);

    boolean existsBySymbol(String symbol);

    List<Stock> findByExchange(String exchange);

    List<Stock> findBySector(String sector);

    // Case-insensitive search on symbol or company name
    @Query("SELECT s FROM Stock s WHERE " +
            "LOWER(s.symbol) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Stock> searchByKeyword(@Param("keyword") String keyword);
}
