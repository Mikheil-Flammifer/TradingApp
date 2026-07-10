package com.my.tradingapp.entity.order;

public enum OrderType {
    MARKET,   // execute immediately at current price
    LIMIT     // execute only at specified price or better
}