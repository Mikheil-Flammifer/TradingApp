# TradingApp — AI-Assisted Stock Momentum Trading Platform

## What is TradingApp?

TradingApp is a smart stock trading assistant that analyzes stock market activity over configurable time periods — from a few days to several weeks — and uses a momentum-based algorithm to identify which stocks show the strongest upward trend. Based on this analysis, the app gives users personalized buy and sell recommendations with suggested target prices, and allows users to set up automatic orders to execute those recommendations hands-free.

The goal is simple: help everyday users make better trading decisions and increase their profits by removing guesswork and emotional bias from stock picking.

---

## Core Features

### Momentum Analysis Engine
- Continuously monitors stock price activity across configurable time windows (days to weeks)
- Calculates upward momentum indicators for each tracked stock
- Ranks stocks by momentum strength and trend consistency
- Identifies optimal entry points based on historical price patterns

### Smart Recommendations
- Recommends which stocks to buy based on momentum score
- Suggests a target sell price calculated from momentum projections
- Shows confidence level and supporting data for each recommendation
- Updates recommendations as new price data comes in

### Automatic Order Execution
- Users can review each recommendation and choose to act manually or set it to automatic
- Automatic mode places buy/sell orders when price conditions are met
- Users stay in full control — automation can be enabled or disabled per stock at any time
- Limit orders are used for precision entry and exit at recommended prices

### Portfolio Management
- Real-time portfolio valuation with unrealized and realized P&L
- Cash balance management with deposit and withdrawal support
- Full transaction history and order audit trail
- Per-stock performance breakdown

### Watchlist
- Users can track stocks they are interested in without buying
- Watchlisted stocks are included in momentum analysis
- Instant alerts when a watchlisted stock receives a strong buy signal

### Account & Security
- Secure registration and login with JWT authentication
- Email OTP verification for account safety
- Password reset via email OTP
- Role-based access (USER / ADMIN)

---

## How the Momentum Algorithm Works

The algorithm evaluates each stock over a user-selected lookback period and scores it across several dimensions:

```
Momentum Score = f(
    Price Rate of Change     (how fast price is rising),
    Trend Consistency        (how steadily it rises without reversals),
    Volume Confirmation      (is rising price backed by rising volume?),
    Relative Strength        (how it performs vs the broader market)
)
```

Stocks scoring above a configurable threshold are flagged as **Buy Candidates**. For each candidate the algorithm projects a target sell price based on the momentum curve and recent resistance levels. The user then decides:

- **Manual** — review the recommendation and place orders themselves
- **Automatic** — the app places a limit buy order at the recommended entry price and a limit sell order at the recommended target price automatically

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 4.x |
| Language | Java 23 |
| Database | PostgreSQL 17 |
| ORM | Hibernate 7 / Spring Data JPA |
| Security | Spring Security 7 + JWT (jjwt) |
| Email | Spring Mail + Brevo SMTP |
| Mapping | MapStruct 1.5 |
| Validation | Jakarta Bean Validation |
| Build | Maven |
| Server | Apache Tomcat 11 (embedded) |

---
## Getting Started

### Prerequisites
- Java 17+
- PostgreSQL 17
- Maven 3.8+

### Setup

1. Clone the repository:
```bash
git clone https://github.com/yourname/TradingApp.git
cd TradingApp
```

2. Create a PostgreSQL database:
```sql
CREATE DATABASE "TradingApp";
```

3. Create a `.env` file in the project root:
```env
DB_USERNAME=postgres
DB_PASSWORD=yourpassword

SMTP_USERNAME=your_smtp_user
SMTP_PASSWORD=your_smtp_password
SMTP_FROM=your@email.com

JWT_SECRET=your-long-random-secret-key-at-least-32-characters
```

4. Run the application:
```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

---

## Disclaimer

TradingApp is a personal portfolio project built for educational purposes. It is not financial advice. Always do your own research before making investment decisions. Past momentum does not guarantee future returns.