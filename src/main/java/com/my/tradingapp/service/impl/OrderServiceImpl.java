package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.request.PlaceOrderRequest;
import com.my.tradingapp.dto.response.OrderResponse;
import com.my.tradingapp.entity.holding.Holding;
import com.my.tradingapp.entity.order.Order;
import com.my.tradingapp.entity.order.OrderSide;
import com.my.tradingapp.entity.order.OrderStatus;
import com.my.tradingapp.entity.order.OrderType;
import com.my.tradingapp.entity.portfolio.Portfolio;
import com.my.tradingapp.entity.stock.Stock;
import com.my.tradingapp.exception.AppException;
import com.my.tradingapp.mapper.OrderMapper;
import com.my.tradingapp.repository.HoldingRepository;
import com.my.tradingapp.repository.OrderRepository;
import com.my.tradingapp.repository.PortfolioRepository;
import com.my.tradingapp.repository.StockRepository;
import com.my.tradingapp.service.OrderService;
import com.my.tradingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final HoldingRepository holdingRepository;
    private final TransactionService transactionService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse placeOrder(Long userId, PlaceOrderRequest request) {
        Portfolio portfolio = findPortfolioByUserId(userId);
        Stock stock = stockRepository.findBySymbol(request.symbol().toUpperCase())
                .orElseThrow(() -> AppException.notFound("Stock not found: " + request.symbol()));

        // Validate order before creating
        validateOrder(request, portfolio, stock);

        // Build order
        Order order = Order.builder()
                .portfolio(portfolio)
                .stock(stock)
                .side(request.side())
                .orderType(request.orderType())
                .status(OrderStatus.PENDING)
                .quantity(request.quantity())
                .limitPrice(request.limitPrice())
                .build();
        orderRepository.save(order);

        // Execute MARKET orders immediately
        if (request.orderType() == OrderType.MARKET) {
            executeOrder(order, stock.getCurrentPrice());
        }

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long userId, Long orderId) {
        Portfolio portfolio = findPortfolioByUserId(userId);
        Order order = orderRepository.findByIdAndPortfolioId(orderId, portfolio.getId())
                .orElseThrow(() -> AppException.notFound("Order not found: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING)
            throw AppException.badRequest("Only PENDING orders can be cancelled");

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getOrders(Long userId) {
        Portfolio portfolio = findPortfolioByUserId(userId);
        return orderMapper.toResponseList(
                orderRepository.findByPortfolioIdOrderByCreatedAtDesc(portfolio.getId()));
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(Long userId, OrderStatus status) {
        Portfolio portfolio = findPortfolioByUserId(userId);
        return orderMapper.toResponseList(
                orderRepository.findByPortfolioIdAndStatusOrderByCreatedAtDesc(
                        portfolio.getId(), status));
    }

    @Override
    public OrderResponse getOrderById(Long userId, Long orderId) {
        Portfolio portfolio = findPortfolioByUserId(userId);
        Order order = orderRepository.findByIdAndPortfolioId(orderId, portfolio.getId())
                .orElseThrow(() -> AppException.notFound("Order not found: " + orderId));
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public void processPendingLimitOrders() {
        List<Order> pendingLimitOrders = orderRepository
                .findByStatusAndOrderType(OrderStatus.PENDING, OrderType.LIMIT);

        for (Order order : pendingLimitOrders) {
            BigDecimal currentPrice = order.getStock().getCurrentPrice();
            if (currentPrice == null) continue;

            boolean shouldFill =
                    (order.getSide() == OrderSide.BUY &&
                            currentPrice.compareTo(order.getLimitPrice()) <= 0) ||
                            (order.getSide() == OrderSide.SELL &&
                                    currentPrice.compareTo(order.getLimitPrice()) >= 0);

            if (shouldFill) {
                try {
                    executeOrder(order, currentPrice);
                    log.info("Filled LIMIT order {} for {} at {}",
                            order.getId(), order.getStock().getSymbol(), currentPrice);
                } catch (Exception e) {
                    log.error("Failed to fill limit order {}: {}", order.getId(), e.getMessage());
                    order.setStatus(OrderStatus.REJECTED);
                    orderRepository.save(order);
                }
            }
        }
    }

    // ── Core execution logic ────────────────────────────────────────────────

    private void executeOrder(Order order, BigDecimal executionPrice) {
        Portfolio portfolio = order.getPortfolio();
        Stock stock = order.getStock();
        BigDecimal totalValue = executionPrice.multiply(order.getQuantity());

        if (order.getSide() == OrderSide.BUY) {
            executeBuy(order, portfolio, stock, executionPrice, totalValue);
        } else {
            executeSell(order, portfolio, stock, executionPrice, totalValue);
        }

        // Mark order as filled
        order.setFilledPrice(executionPrice);
        order.setTotalValue(totalValue);
        order.setStatus(OrderStatus.FILLED);
        order.setFilledAt(Instant.now());
        orderRepository.save(order);

        // Create transaction record
        transactionService.createTransaction(order);
    }

    private void executeBuy(Order order, Portfolio portfolio, Stock stock,
                            BigDecimal price, BigDecimal totalValue) {
        // Check sufficient funds
        if (portfolio.getCashBalance().compareTo(totalValue) < 0)
            throw AppException.badRequest("Insufficient cash balance");

        // Deduct cash
        portfolio.setCashBalance(portfolio.getCashBalance().subtract(totalValue));
        portfolioRepository.save(portfolio);

        // Update or create holding
        Optional<Holding> existingHolding = holdingRepository
                .findByPortfolioIdAndStockId(portfolio.getId(), stock.getId());

        if (existingHolding.isPresent()) {
            Holding holding = existingHolding.get();
            // Recalculate average buy price
            BigDecimal totalCost = holding.getTotalCost().add(totalValue);
            BigDecimal totalQty = holding.getQuantity().add(order.getQuantity());
            BigDecimal newAvgPrice = totalCost.divide(totalQty, 4, RoundingMode.HALF_UP);
            holding.setQuantity(totalQty);
            holding.setAvgBuyPrice(newAvgPrice);
            holding.setTotalCost(totalCost);
            holdingRepository.save(holding);
        } else {
            Holding holding = Holding.builder()
                    .portfolio(portfolio)
                    .stock(stock)
                    .quantity(order.getQuantity())
                    .avgBuyPrice(price)
                    .totalCost(totalValue)
                    .build();
            holdingRepository.save(holding);
        }
    }

    private void executeSell(Order order, Portfolio portfolio, Stock stock,
                             BigDecimal price, BigDecimal totalValue) {
        Holding holding = holdingRepository
                .findByPortfolioIdAndStockId(portfolio.getId(), stock.getId())
                .orElseThrow(() -> AppException.badRequest(
                        "You do not hold any shares of " + stock.getSymbol()));

        if (holding.getQuantity().compareTo(order.getQuantity()) < 0)
            throw AppException.badRequest("Insufficient shares. You hold "
                    + holding.getQuantity() + " shares of " + stock.getSymbol());

        // Add cash from sale
        portfolio.setCashBalance(portfolio.getCashBalance().add(totalValue));
        portfolioRepository.save(portfolio);

        // Update holding quantity
        BigDecimal newQty = holding.getQuantity().subtract(order.getQuantity());
        if (newQty.compareTo(BigDecimal.ZERO) == 0) {
            // Sold all shares — remove holding entirely
            holdingRepository.delete(holding);
        } else {
            BigDecimal newTotalCost = holding.getAvgBuyPrice().multiply(newQty);
            holding.setQuantity(newQty);
            holding.setTotalCost(newTotalCost);
            holdingRepository.save(holding);
        }
    }

    // ── Validation ──────────────────────────────────────────────────────────

    private void validateOrder(PlaceOrderRequest request, Portfolio portfolio, Stock stock) {
        if (stock.getCurrentPrice() == null)
            throw AppException.badRequest("Stock price unavailable for: " + stock.getSymbol());

        if (request.orderType() == OrderType.LIMIT && request.limitPrice() == null)
            throw AppException.badRequest("Limit price is required for LIMIT orders");

        if (request.side() == OrderSide.BUY) {
            BigDecimal price = request.orderType() == OrderType.LIMIT
                    ? request.limitPrice()
                    : stock.getCurrentPrice();
            BigDecimal totalCost = price.multiply(request.quantity());
            if (portfolio.getCashBalance().compareTo(totalCost) < 0)
                throw AppException.badRequest(
                        "Insufficient funds. Required: " + totalCost
                                + ", Available: " + portfolio.getCashBalance());
        }

        if (request.side() == OrderSide.SELL) {
            holdingRepository.findByPortfolioIdAndStockId(
                            portfolio.getId(), stock.getId())
                    .ifPresentOrElse(
                            h -> {
                                if (h.getQuantity().compareTo(request.quantity()) < 0)
                                    throw AppException.badRequest(
                                            "Insufficient shares. You hold "
                                                    + h.getQuantity() + " shares");
                            },
                            () -> { throw AppException.badRequest(
                                    "You do not hold any shares of " + stock.getSymbol()); }
                    );
        }
    }

    private Portfolio findPortfolioByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> AppException.notFound("Portfolio not found"));
    }
}