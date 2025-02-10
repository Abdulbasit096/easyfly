package com.abdulbasit.flypath.utils;

import io.github.cdimascio.dotenv.Dotenv;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Component
public class CurrencyConversion {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String BASE_URL = String.format("https://v6.exchangerate-api.com/v6/%s/pair/EUR/PKR",
            dotenv.get("EXCHANGE_RATE_API_KEY"));
    Logger logger = LoggerFactory.getLogger(CurrencyConversion.class);

        private record ExchangeRateResponse(String result, double conversionRate) {

        public boolean isSuccess() {
                return "success".equals(result);
            }

            public static ExchangeRateResponse fromJson(String json) {
                try {
                    json = json.replaceAll("\\s", "");
                    String result = extractValue(json, "result");
                    double rate = Double.parseDouble(extractValue(json, "conversion_rate"));
                    return new ExchangeRateResponse(result, rate);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to parse API response: " + e.getMessage());
                }
            }

            private static String extractValue(String json, String key) {
                String searchKey = "\"" + key + "\":";
                int start = json.indexOf(searchKey) + searchKey.length();
                if (start == -1) {
                    throw new IllegalArgumentException("Key not found: " + key);
                }

                if (json.charAt(start) == '"') {
                    start++; // Skip opening quote
                    int end = json.indexOf('"', start);
                    return json.substring(start, end);
                }
                else {
                    int end = json.indexOf(',', start);
                    if (end == -1) {
                        end = json.indexOf('}', start);
                    }
                    return json.substring(start, end);
                }
            }
        }

    public double getExchangeRate() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API call failed with status code: " + response.statusCode());
        }

        ExchangeRateResponse exchangeRate = ExchangeRateResponse.fromJson(response.body());

        if (!exchangeRate.isSuccess()) {
            throw new IOException("API call was not successful");
        }

        return exchangeRate.conversionRate();
    }

    public int convertEurToPkr(double eurAmount) throws IOException, InterruptedException {
        if (eurAmount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        try{
            double rate = getExchangeRate();
            return (int) (eurAmount * rate);
        }catch (IOException  | InterruptedException e){
            logger.error("Conversion Error "+e.getMessage());
            throw e;

        }

    }
}