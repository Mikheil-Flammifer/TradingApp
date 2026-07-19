package com.my.tradingapp.controller;

import com.my.tradingapp.dto.request.PlaceOrderRequest;
import com.my.tradingapp.dto.response.ApiResponse;
import com.my.tradingapp.dto.response.OrderResponse;
import com.my.tradingapp.dto.response.UserResponse;
import com.my.tradingapp.entity.order.OrderStatus;
import com.my.tradingapp.service.OrderService;
import com.my.tradingapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    // Place a new BUY or SELL order
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PlaceOrderRequest request) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        OrderResponse response = orderService.placeOrder(user.id(), request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order placed successfully", response));
    }

    // Get all orders for current user
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) OrderStatus status) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        List<OrderResponse> response = status != null
                ? orderService.getOrdersByStatus(user.id(), status)
                : orderService.getOrders(user.id());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Get single order by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        OrderResponse response = orderService.getOrderById(user.id(), id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Cancel a pending order
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        UserResponse user = userService.getUserByEmail(userDetails.getUsername());
        OrderResponse response = orderService.cancelOrder(user.id(), id);
        return ResponseEntity.ok(ApiResponse.success("Order cancelled successfully", response));
    }
}
