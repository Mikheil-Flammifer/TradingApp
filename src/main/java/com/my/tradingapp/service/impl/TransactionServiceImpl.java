package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.response.TransactionResponse;
import com.my.tradingapp.entity.order.Order;
import com.my.tradingapp.entity.order.OrderSide;
import com.my.tradingapp.entity.transaction.Transaction;
import com.my.tradingapp.entity.holding.Holding;
import com.my.tradingapp.exception.AppException;
import com.my.tradingapp.mapper.TransactionMapper;
import com.my.tradingapp.repository.HoldingRepository;
import com.my.tradingapp.repository.PortfolioRepository;
import com.my.tradingapp.repository.StockRepository;
import com.my.tradingapp.repository.TransactionRepository;
import com.my.tradingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final HoldingRepository holdingRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public List<TransactionResponse> getTransactions(Long userId) {
        Long portfolioId = getPortfolioId(userId);
        return transactionMapper.toResponseList(
                transactionRepository.findByPortfolioIdOrderByExecutedAtDesc(portfolioId));
    }

    @Override
    public List<TransactionResponse> getTransactionsByStock(Long userId, String symbol) {
        Long portfolioId = getPortfolioId(userId);
        Long stockId = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> AppException.notFound("Stock not found: " + symbol))
                .getId();
        return transactionMapper.toResponseList(
                transactionRepository.findByPortfolioIdAndStockIdOrderByExecutedAtDesc(
                        portfolioId, stockId));
    }

    @Override
    public TransactionResponse getTransactionById(Long userId, Long transactionId) {
        Long portfolioId = getPortfolioId(userId);
        Transaction transaction = transactionRepository
                .findByIdAndPortfolioId(transactionId, portfolioId)
                .orElseThrow(() -> AppException.notFound(
                        "Transaction not found: " + transactionId));
        return transactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional
    public void createTransaction(Order order) {
        BigDecimal totalValue = order.getFilledPrice().multiply(order.getQuantity());

        // Calculate realized P&L for SELL orders
        BigDecimal realizedPnl = null;
        if (order.getSide() == OrderSide.SELL) {
            Optional<Holding> holding = holdingRepository
                    .findByPortfolioIdAndStockId(
                            order.getPortfolio().getId(),
                            order.getStock().getId());
            if (holding.isPresent()) {
                BigDecimal costBasis = holding.get().getAvgBuyPrice()
                        .multiply(order.getQuantity());
                realizedPnl = totalValue.subtract(costBasis)
                        .setScale(4, RoundingMode.HALF_UP);
            }
        }

        Transaction transaction = Transaction.builder()
                .order(order)
                .stock(order.getStock())
                .portfolio(order.getPortfolio())
                .side(order.getSide())
                .quantity(order.getQuantity())
                .price(order.getFilledPrice())
                .totalValue(totalValue)
                .realizedPnl(realizedPnl)
                .build();

        transactionRepository.save(transaction);
    }

    private Long getPortfolioId(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> AppException.notFound("Portfolio not found"))
                .getId();
    }
}