package com.stocktracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocktracker.model.StockPrice;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    public StockPrice fetchStockPrice(String symbol) throws IOException {
        String url = String.format("%s?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
                API_URL, symbol, apiKey);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            String response = client.execute(request, httpResponse -> EntityUtils.toString(httpResponse.getEntity()));

            JsonNode root = objectMapper.readTree(response);
            JsonNode quote = root.get("Global Quote");

            if (quote == null) {
                throw new IOException("Failed to fetch stock data");
            }

            return new StockPrice(
                    symbol,
                    Double.parseDouble(quote.get("05. price").asText()),
                    quote.get("07. latest trading day").asText());
        }
    }
}