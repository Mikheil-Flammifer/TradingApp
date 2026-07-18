package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.response.TransactionResponse;
import com.my.tradingapp.entity.Order;
import com.my.tradingapp.mapper.TransactionMapper;
import com.my.tradingapp.repository.PortfolioRepository;
import com.my.tradingapp.repository.StockRepository;
import com.my.tradingapp.repository.TransactionRepository;
import com.my.tradingapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public List<TransactionResponse> getTransactions(Long userId) {
        // TODO:
        // 1. Find portfolio by userId
        // 2. Fetch all transactions for portfolio ordered by executedAt DESC
        // 3. Map and return list
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<TransactionResponse> getTransactionsByStock(Long userId, String symbol) {
        // TODO:
        // 1. Find portfolio by userId
        // 2. Find stock by symbol
        // 3. Fetch transactions filtered by portfolioId and stockId
        // 4. Map and return list
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public TransactionResponse getTransactionById(Long userId, Long transactionId) {
        // TODO:
        // 1. Find transaction by id — throw NotFoundException if not found
        // 2. Verify it belongs to this user's portfolio
        // 3. Map and return TransactionResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void createTransaction(Order order) {
        // TODO:
        // Called internally by OrderService when order is FILLED
        // 1. Calculate totalValue = order.filledPrice * order.quantity
        // 2. For SELL orders calculate realizedPnl:
        //    realizedPnl = (filledPrice - holding.avgBuyPrice) * quantity
        // 3. Build Transaction entity from order
        // 4. Save transaction
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
