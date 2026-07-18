package com.my.tradingapp.service;

import com.my.tradingapp.dto.request.PlaceOrderRequest;
import com.my.tradingapp.dto.response.OrderResponse;
import com.my.tradingapp.entity.order.OrderStatus;

import java.util.List;

public interface OrderService {

    // Place a new BUY or SELL order
    OrderResponse placeOrder(Long userId, PlaceOrderRequest request);

    // Cancel a PENDING order
    OrderResponse cancelOrder(Long userId, Long orderId);

    // Get all orders for a user
    List<OrderResponse> getOrders(Long userId);

    // Get orders filtered by status e.g. PENDING, FILLED
    List<OrderResponse> getOrdersByStatus(Long userId, OrderStatus status);

    // Get single order by id
    OrderResponse getOrderById(Long userId, Long orderId);

    // Called by scheduled job — tries to fill pending LIMIT orders
    // when current price matches the limit price
    void processPendingLimitOrders();
}
