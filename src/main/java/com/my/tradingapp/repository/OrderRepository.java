package com.my.tradingapp.repository;

import com.my.tradingapp.entity.order.Order;
import com.my.tradingapp.entity.order.OrderSide;
import com.my.tradingapp.entity.order.OrderStatus;
import com.my.tradingapp.entity.order.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByPortfolioIdOrderByCreatedAtDesc(Long portfolioId);

    List<Order> findByPortfolioIdAndStatusOrderByCreatedAtDesc(Long portfolioId, OrderStatus status);

    Optional<Order> findByIdAndPortfolioId(Long id, Long portfolioId);

    // Used by scheduler to find pending LIMIT orders to process
    List<Order> findByStatusAndOrderType(OrderStatus status, OrderType orderType);

    // Used by scheduler — filter further by side (BUY or SELL)
    List<Order> findByStatusAndOrderTypeAndSide(OrderStatus status, OrderType orderType, OrderSide side);
}
