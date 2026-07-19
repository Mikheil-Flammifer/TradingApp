package com.my.tradingapp.service.impl;

import com.my.tradingapp.dto.response.HoldingResponse;
import com.my.tradingapp.dto.response.PortfolioResponse;
import com.my.tradingapp.entity.portfolio.Portfolio;
import com.my.tradingapp.entity.user.User;
import com.my.tradingapp.exception.AppException;
import com.my.tradingapp.mapper.HoldingMapper;
import com.my.tradingapp.mapper.PortfolioMapper;
import com.my.tradingapp.repository.PortfolioRepository;
import com.my.tradingapp.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final HoldingMapper holdingMapper;

    @Override
    public PortfolioResponse getPortfolio(Long userId) {
        Portfolio portfolio = findByUserId(userId);

        // Calculate P&L for each holding
        List<HoldingResponse> holdingResponses = portfolio.getHoldings().stream()
                .map(holding -> {
                    BigDecimal currentPrice = holding.getStock().getCurrentPrice();
                    BigDecimal currentValue = currentPrice.multiply(holding.getQuantity());
                    BigDecimal unrealizedPnl = currentValue.subtract(holding.getTotalCost());
                    BigDecimal unrealizedPnlPct = holding.getTotalCost().compareTo(BigDecimal.ZERO) == 0
                            ? BigDecimal.ZERO
                            : unrealizedPnl.divide(holding.getTotalCost(), 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                    return holdingMapper.toResponse(
                            holding, currentPrice, currentValue, unrealizedPnl, unrealizedPnlPct);
                })
                .toList();

        // Sum all holding values
        BigDecimal totalHoldingsValue = holdingResponses.stream()
                .map(HoldingResponse::currentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPortfolioValue = portfolio.getCashBalance().add(totalHoldingsValue);
        BigDecimal totalPnl = totalPortfolioValue.subtract(portfolio.getTotalDeposited());
        BigDecimal totalPnlPct = portfolio.getTotalDeposited().compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : totalPnl.divide(portfolio.getTotalDeposited(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return portfolioMapper.toResponse(
                portfolio, totalHoldingsValue, totalPortfolioValue,
                totalPnl, totalPnlPct, holdingResponses);
    }

    @Override
    @Transactional
    public PortfolioResponse deposit(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw AppException.badRequest("Deposit amount must be greater than zero");

        Portfolio portfolio = findByUserId(userId);
        portfolio.setCashBalance(portfolio.getCashBalance().add(amount));
        portfolio.setTotalDeposited(portfolio.getTotalDeposited().add(amount));
        portfolioRepository.save(portfolio);
        return getPortfolio(userId);
    }

    @Override
    @Transactional
    public PortfolioResponse withdraw(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw AppException.badRequest("Withdrawal amount must be greater than zero");

        Portfolio portfolio = findByUserId(userId);
        if (portfolio.getCashBalance().compareTo(amount) < 0)
            throw AppException.badRequest("Insufficient cash balance");

        portfolio.setCashBalance(portfolio.getCashBalance().subtract(amount));
        portfolio.setTotalDeposited(portfolio.getTotalDeposited().subtract(amount));
        portfolioRepository.save(portfolio);
        return getPortfolio(userId);
    }

    @Override
    @Transactional
    public void updateCashBalance(Long portfolioId, BigDecimal amount) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> AppException.notFound("Portfolio not found"));
        portfolio.setCashBalance(portfolio.getCashBalance().add(amount));
        portfolioRepository.save(portfolio);
    }

    @Override
    @Transactional
    public void createPortfolioForUser(User user) {
        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .cashBalance(BigDecimal.valueOf(10000.00))
                .totalDeposited(BigDecimal.valueOf(10000.00))
                .build();
        portfolioRepository.save(portfolio);
    }

    private Portfolio findByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> AppException.notFound("Portfolio not found for user: " + userId));
    }
}