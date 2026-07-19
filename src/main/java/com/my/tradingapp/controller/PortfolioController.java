package com.my.tradingapp.controller;

import com.my.tradingapp.dto.response.ApiResponse;
import com.my.tradingapp.dto.response.PortfolioResponse;
import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.service.PortfolioService;
import com.my.tradingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserService userService;

    // Get current user's portfolio with holdings and P&L
    @GetMapping
    public ResponseEntity<ApiResponse<PortfolioResponse>> getPortfolio(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        PortfolioResponse response = portfolioService.getPortfolio(user.id());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Deposit cash into portfolio
    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<PortfolioResponse>> deposit(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam BigDecimal amount) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        PortfolioResponse response = portfolioService.deposit(user.id(), amount);
        return ResponseEntity.ok(ApiResponse.success("Deposit successful", response));
    }

    // Withdraw cash from portfolio
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<PortfolioResponse>> withdraw(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam BigDecimal amount) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        PortfolioResponse response = portfolioService.withdraw(user.id(), amount);
        return ResponseEntity.ok(ApiResponse.success("Withdrawal successful", response));
    }
}
