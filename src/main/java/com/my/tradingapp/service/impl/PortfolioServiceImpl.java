package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.response.PortfolioResponse;
import com.my.tradingapp.entity.user.User;
import com.my.tradingapp.mapper.HoldingMapper;
import com.my.tradingapp.mapper.PortfolioMapper;
import com.my.tradingapp.repository.PortfolioRepository;
import com.my.tradingapp.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final HoldingMapper holdingMapper;

    @Override
    public PortfolioResponse getPortfolio(Long userId) {
        // TODO:
        // 1. Find portfolio by userId — throw NotFoundException if not found
        // 2. For each holding calculate:
        //    - currentValue = holding.quantity * stock.currentPrice
        //    - unrealizedPnl = currentValue - holding.totalCost
        //    - unrealizedPnlPct = unrealizedPnl / holding.totalCost * 100
        // 3. Sum all holding currentValues → totalHoldingsValue
        // 4. totalPortfolioValue = cashBalance + totalHoldingsValue
        // 5. totalPnl = totalPortfolioValue - totalDeposited
        // 6. totalPnlPct = totalPnl / totalDeposited * 100
        // 7. Map and return PortfolioResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public PortfolioResponse deposit(Long userId, BigDecimal amount) {
        // TODO:
        // 1. Validate amount > 0
        // 2. Find portfolio by userId
        // 3. Add amount to cashBalance and totalDeposited
        // 4. Save and return updated PortfolioResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public PortfolioResponse withdraw(Long userId, BigDecimal amount) {
        // TODO:
        // 1. Validate amount > 0
        // 2. Find portfolio by userId
        // 3. Check cashBalance >= amount — throw InsufficientFundsException if not
        // 4. Subtract amount from cashBalance and totalDeposited
        // 5. Save and return updated PortfolioResponse
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void updateCashBalance(Long portfolioId, BigDecimal amount) {
        // TODO:
        // 1. Find portfolio by id
        // 2. Add amount to cashBalance (negative amount = deduction for BUY)
        // 3. Save portfolio
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    @Transactional
    public void createPortfolioForUser(User user) {
        // TODO:
        // 1. Create new Portfolio with default cashBalance (e.g. 10,000)
        // 2. Set user
        // 3. Save portfolio
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
