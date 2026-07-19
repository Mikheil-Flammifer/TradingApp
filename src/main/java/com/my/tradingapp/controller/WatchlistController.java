package com.my.tradingapp.controller;

import com.my.tradingapp.dto.request.WatchlistRequest;
import com.my.tradingapp.dto.response.ApiResponse;
import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.dto.response.WatchlistResponse;
import com.my.tradingapp.service.UserService;
import com.my.tradingapp.service.WatchlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final UserService userService;

    // Get current user's watchlist
    @GetMapping
    public ResponseEntity<ApiResponse<WatchlistResponse>> getWatchlist(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        WatchlistResponse response = watchlistService.getWatchlist(user.id());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Add stock to watchlist
    @PostMapping
    public ResponseEntity<ApiResponse<WatchlistResponse>> addStock(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody WatchlistRequest request) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        WatchlistResponse response = watchlistService.addStock(user.id(), request);
        return ResponseEntity.ok(ApiResponse.success("Stock added to watchlist", response));
    }

    // Remove stock from watchlist
    @DeleteMapping("/{symbol}")
    public ResponseEntity<ApiResponse<WatchlistResponse>> removeStock(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String symbol) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        WatchlistResponse response = watchlistService.removeStock(user.id(), symbol.toUpperCase());
        return ResponseEntity.ok(ApiResponse.success("Stock removed from watchlist", response));
    }
}
