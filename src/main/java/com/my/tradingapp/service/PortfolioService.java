package com.my.tradingapp.service;

import com.my.tradingapp.dto.response.PortfolioResponse;

public interface PortfolioService {

    // Get full portfolio with holdings and calculated P&L
    PortfolioResponse getPortfolio(Long userId);

    // Deposit cash into portfolio
    PortfolioResponse deposit(Long userId, java.math.BigDecimal amount);

    // Withdraw cash from portfolio
    PortfolioResponse withdraw(Long userId, java.math.BigDecimal amount);

    // Called internally when an order is filled — updates cash balance
    void updateCashBalance(Long portfolioId, java.math.BigDecimal amount);

    // Called internally on register — creates a fresh portfolio for a new user
    void createPortfolioForUser(com.my.tradingapp.entity.user.User user);
}
