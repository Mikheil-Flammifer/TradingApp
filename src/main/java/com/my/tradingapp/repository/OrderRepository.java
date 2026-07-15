package com.my.tradingapp.repository;

import com.my.tradingapp.entity.order.Order;
import com.my.tradingapp.entity.order.OrderStatus;
import com.my.tradingapp.entity.order.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPortfolioId(Long portfolioId);
    List<Order> findByPortfolioIdAndStatus(Long portfolioId, OrderStatus status);
    List<Order> findByStockSymbolAndStatusAndOrderType(String symbol, OrderStatus status, OrderType orderType);
    // ^ used by order execution engine to find pending LIMIT orders
}