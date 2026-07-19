package com.my.tradingapp.client;

import com.my.tradingapp.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@Component
public class MarketDataClient {

    // Using Twelve Data API — free tier gives 800 requests/day
    // Sign up at https://twelvedata.com to get a free API key
    @Value("${market.api.key:demo}")
    private String apiKey;

    private final RestClient restClient;

    public MarketDataClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.twelvedata.com")
                .build();
    }

    // Fetch latest price for a single stock symbol
    public BigDecimal fetchCurrentPrice(String symbol) {
        try {
            Map response = restClient.get()
                    .uri("/price?symbol={symbol}&apikey={apikey}", symbol, apiKey)
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.containsKey("price")) {
                return new BigDecimal(response.get("price").toString());
            }

            log.warn("No price returned for symbol: {}", symbol);
            return null;
        } catch (Exception e) {
            log.error("Failed to fetch price for {}: {}", symbol, e.getMessage());
            return null;
        }
    }

    // Fetch OHLCV data for price history
    public Map fetchOhlcv(String symbol, String interval, int outputSize) {
        try {
            return restClient.get()
                    .uri("/time_series?symbol={symbol}&interval={interval}&outputsize={size}&apikey={apikey}",
                            symbol, interval, outputSize, apiKey)
                    .retrieve()
                    .body(Map.class);
        } catch (Exception e) {
            log.error("Failed to fetch OHLCV for {}: {}", symbol, e.getMessage());
            return null;
        }
    }
}
