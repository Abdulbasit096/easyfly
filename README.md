# ✈️ EasyFly

**EasyFly** is a Spring Boot REST API backend for searching and comparing flights. It integrates with the [Amadeus Flight Offers API](https://developers.amadeus.com/) to fetch real-time flight data, applies graph-based routing algorithms to find the cheapest or shortest routes, and uses Redis caching to optimise repeated queries. Prices are automatically converted from EUR to PKR.

---

## 🚀 Features

- 🔍 **Flight Search** — Search for flights by origin, destination, departure date, return date, and number of adults
- 💸 **Cheapest Route** — Uses a graph algorithm (Dijkstra-style) to find the lowest-priced itinerary
- ⚡ **Shortest Route** — Finds the fastest (fewest stops / least duration) route between two airports
- 💱 **Currency Conversion** — Automatically converts flight prices from EUR to PKR using an exchange rate API
- 🗃️ **Redis Caching** — Caches flight search results to reduce redundant Amadeus API calls
- 🌐 **CORS Support** — Pre-configured for frontend on `http://localhost:3000`
- 📊 **Airline & Airport Data** — Bundled CSV datasets for airlines and airports

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.4.2 |
| Flight Data | Amadeus Java SDK 7.0.0 |
| Caching | Spring Data Redis 3.4.2 |
| Utilities | Google Guava 33.4.0 |
| Config | dotenv-java 3.1.0 |
| Build | Maven (via Maven Wrapper) |

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/com/abdulbasit/flypath/
    │   ├── FlyPathApplication.java       # Spring Boot entry point
    │   ├── config/                       # App configuration (Redis, caching, etc.)
    │   ├── controller/
    │   │   ├── FlightController.java     # Flight search endpoints
    │   │   ├── AirlineController.java    # Airline lookup endpoints
    │   │   └── AirportController.java    # Airport lookup endpoints
    │   ├── service/
    │   │   ├── AmadeusAPIService.java    # Amadeus API integration & caching
    │   │   ├── FlightService.java        # Flight business logic & graph loading
    │   │   ├── AirlineService.java       # Airline data service
    │   │   └── AirportService.java       # Airport data service
    │   ├── model/
    │   │   ├── Flight.java
    │   │   ├── FlightRequest.java
    │   │   ├── Itinerary.java
    │   │   ├── RouteModel.java
    │   │   ├── Airline.java
    │   │   └── Airport.java
    │   └── utils/
    │       ├── FlightGraph.java          # Graph-based route algorithms
    │       └── CurrencyConversion.java   # EUR → PKR conversion
    └── resources/
        ├── application.properties
        ├── airlines.csv                  # Bundled airline data
        └── airports.csv                  # Bundled airport data
```

---

## 📡 API Endpoints

### Flights

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/flights/search` | Search for available flights |

#### Query Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `origin` | `String` | ✅ | IATA airport code (e.g. `LHR`) |
| `destination` | `String` | ✅ | IATA airport code (e.g. `JFK`) |
| `departureDate` | `String` | ✅ | Date in `YYYY-MM-DD` format |
| `returnDate` | `String` | ✅ | Date in `YYYY-MM-DD` format |
| `adults` | `int` | ✅ | Number of adult passengers |
| `cheapest` | `Boolean` | ❌ | `true` = cheapest route, `false` = shortest route, omit = all flights |

#### Example Requests

```bash
# Get all available flights
GET /api/flights/search?origin=LHR&destination=JFK&departureDate=2025-06-01&returnDate=2025-06-15&adults=1

# Get the cheapest route
GET /api/flights/search?origin=LHR&destination=JFK&departureDate=2025-06-01&returnDate=2025-06-15&adults=1&cheapest=true

# Get the shortest (fastest) route
GET /api/flights/search?origin=LHR&destination=JFK&departureDate=2025-06-01&returnDate=2025-06-15&adults=1&cheapest=false
```

### Airlines & Airports

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/airlines/...` | Airline lookup |
| `GET` | `/api/airports/...` | Airport lookup |

---

## ⚙️ Configuration & Environment Variables

The application uses `dotenv-java` to load environment variables from a `.env` file. Create a `.env` file in the project root with the following:

```env
# Amadeus API credentials
# Get yours at https://developers.amadeus.com/
API_KEY=your_amadeus_api_key
API_SECRET=your_amadeus_api_secret

# Exchange Rate API
# Get yours at https://www.exchangerate-api.com/
EXCHANGE_RATE_API_KEY=your_exchange_rate_api_key

# Redis connection
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=your_redis_password
```

> ⚠️ **Never commit your `.env` file.** It is already listed in `.gitignore`.

---

## 🏃 Getting Started

### Prerequisites

- Java 21+
- Maven (or use the included `mvnw` wrapper)
- A running Redis instance
- Amadeus API credentials (free tier available at [developers.amadeus.com](https://developers.amadeus.com/))
- Exchange Rate API key (free tier at [exchangerate-api.com](https://developers.amadeus.com/))

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/Abdulbasit096/easyfly.git
   cd easyfly
   ```

2. **Create your `.env` file** (see [Configuration](#️-configuration--environment-variables) above)

3. **Start Redis** (if not already running)
   ```bash
   # Using Docker
   docker run -d -p 6379:6379 redis

   # Or start your local Redis service
   redis-server
   ```

4. **Build and run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   On Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

5. The API will be available at `http://localhost:8080`

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## 📄 License

This project currently has no license specified.