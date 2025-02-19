package com.stocktracker.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocktracker.model.StockPrice;

public class StockPriceService {
    private final String apiKey;
    private final ObjectMapper objectMapper;
    private static final String API_URL = "https://www.alphavantage.co/query";

    public StockPriceService() {
        this.objectMapper = new ObjectMapper();
        this.apiKey = loadApiKey();
    }

    private String loadApiKey() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("alpha.vantage.api.key");
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load API key", ex);
        }
    }

    public StockPrice fetchCurrentPrice(String symbol) throws IOException {
        String url = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s", 
            API_URL, symbol, apiKey);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            String response = client.execute(request, httpResponse ->
                EntityUtils.toString(httpResponse.getEntity()));

            JsonNode root = objectMapper.readTree(response);
            JsonNode quote = root.get("Global Quote");

            if (quote == null) {
                throw new IOException("Failed to fetch stock data");
            }

            return new StockPrice(
                symbol,
                Double.parseDouble(quote.get("05. price").asText()),
                LocalDateTime.now()
            );
        }
    }

    public List<StockPrice> fetchHistoricalData(String symbol) throws IOException {
        String url = String.format("%s?function=TIME_SERIES_INTRADAY&symbol=%s&interval=60min&apikey=%s", 
            API_URL, symbol, apiKey);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            String response = client.execute(request, httpResponse ->
                EntityUtils.toString(httpResponse.getEntity()));

            JsonNode root = objectMapper.readTree(response);
            JsonNode timeSeries = root.get("Time Series (60min)");

            if (timeSeries == null) {
                throw new IOException("Failed to fetch historical data");
            }

            List<StockPrice> historicalPrices = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            timeSeries.fields().forEachRemaining(entry -> {
                double price = Double.parseDouble(entry.getValue().get("4. close").asText());
                LocalDateTime timestamp = LocalDateTime.parse(entry.getKey(), formatter);
                historicalPrices.add(new StockPrice(symbol, price, timestamp));
            });

            return historicalPrices;
        }
    }
}