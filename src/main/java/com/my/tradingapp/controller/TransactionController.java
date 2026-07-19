package com.my.tradingapp.controller;

import com.my.tradingapp.dto.response.ApiResponse;
import com.my.tradingapp.dto.response.TransactionResponse;
import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.service.TransactionService;
import com.my.tradingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    // Get all transactions for current user
    @GetMapping
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactions(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        List<TransactionResponse> response = transactionService.getTransactions(user.id());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Get transactions filtered by stock symbol e.g. GET /api/transactions?symbol=AAPL
    @GetMapping("/stock/{symbol}")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionsByStock(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String symbol) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        List<TransactionResponse> response = transactionService
                .getTransactionsByStock(user.id(), symbol.toUpperCase());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Get single transaction by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        TransactionResponse response = transactionService.getTransactionById(user.id(), id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
