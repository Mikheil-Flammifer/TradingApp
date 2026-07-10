package com.my.tradingapp.entity.order;

public enum OrderStatus {
    PENDING,    // waiting to be executed
    FILLED,     // fully executed
    CANCELLED,  // cancelled by user
    REJECTED    // rejected (e.g. insufficient funds)
}