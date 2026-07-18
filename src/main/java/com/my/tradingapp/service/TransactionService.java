package com.my.tradingapp.service;

import com.my.tradingapp.dto.response.TransactionResponse;
import com.my.tradingapp.entity.order.Order;

import java.util.List;

public interface TransactionService {

    // Get all transactions for a user
    List<TransactionResponse> getTransactions(Long userId);

    // Get transactions for a specific stock
    List<TransactionResponse> getTransactionsByStock(Long userId, String symbol);

    // Get single transaction
    TransactionResponse getTransactionById(Long userId, Long transactionId);

    // Called internally by OrderService when an order is filled
    void createTransaction(Order order);
}
