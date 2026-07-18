package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.request.PlaceOrderRequest;
import com.my.tradingapp.dto.response.OrderResponse;
import com.my.tradingapp.entity.OrderStatus;
import com.my.tradingapp.mapper.OrderMapper;
import com.my.tradingapp.repository.OrderRepository;
import com.my.tradingapp.repository.PortfolioRepository;
import com.my.tradingapp.repository.StockRepository;
import com.my.tradingapp.service.OrderService;
import com.my.tradingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final TransactionService transactionService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse placeOrder(Long userId, PlaceOrderRequest request) {
        // TODO:
        // 1. Find portfolio by userId
        // 2. Find stock by symbol
        // 3. Validate order:
        //    - BUY: check cashBalance >= quantity * currentPrice
        //    - SELL: check holding exists and quantity >= requested quantity
        // 4. Create Order entity with status PENDING
        // 5. Save order
        // 6. For MARKET orders — execute immediately:
        //    a. Set filledPrice = stock.currentPrice
        //    b. Set status = FILLED, filledAt = now
        //    c. Update portfolio cashBalance
        //    d. Update or create Holding
        //    e. Create Transaction via transactionService
        // 7. For LIMIT orders — save as PENDING (processed by scheduler)
        // 8. Return OrderResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long userId, Long orderId) {
        // TODO:
        // 1. Find order by id
        // 2. Verify order belongs to this user's portfolio
        // 3. Check order status is PENDING — throw exception if already FILLED/CANCELLED
        // 4. Set status = CANCELLED
        // 5. Save and return OrderResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<OrderResponse> getOrders(Long userId) {
        // TODO:
        // 1. Find portfolio by userId
        // 2. Fetch all orders for portfolio ordered by createdAt DESC
        // 3. Map and return list
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(Long userId, OrderStatus status) {
        // TODO:
        // 1. Find portfolio by userId
        // 2. Fetch orders filtered by status
        // 3. Map and return list
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public OrderResponse getOrderById(Long userId, Long orderId) {
        // TODO:
        // 1. Find order by id
        // 2. Verify it belongs to this user's portfolio
        // 3. Map and return OrderResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void processPendingLimitOrders() {
        // TODO:
        // Called by @Scheduled job every minute
        // 1. Fetch all PENDING LIMIT orders
        // 2. For each order check if stock.currentPrice meets limit condition:
        //    - BUY LIMIT: currentPrice <= limitPrice
        //    - SELL LIMIT: currentPrice >= limitPrice
        // 3. If condition met — execute the order same as MARKET order above
        // 4. If not — leave as PENDING
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
