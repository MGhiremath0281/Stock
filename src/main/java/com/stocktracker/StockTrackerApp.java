package com.stocktracker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.stocktracker.service.StockPriceService;
import com.stocktracker.ui.StockPriceWindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class StockTrackerApp extends Application {
    private static final int UPDATE_INTERVAL = 60; // seconds
    private final StockPriceService stockPriceService;
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> updateTask;

    public StockTrackerApp() {
        this.stockPriceService = new StockPriceService();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void start(Stage primaryStage) {
        StockPriceWindow window = new StockPriceWindow();
        window.show();

        // Set up the callback for symbol changes
        window.setOnSymbolChanged(() -> {
            if (updateTask != null) {
                updateTask.cancel(false);
            }
            updateStockData(window);
            scheduleUpdates(window);
        });

        // Initial update
        updateStockData(window);
        scheduleUpdates(window);
    }

    private void updateStockData(StockPriceWindow window) {
        try {
            String symbol = window.getSelectedSymbol();
            var currentPrice = stockPriceService.fetchCurrentPrice(symbol);
            var historicalPrices = stockPriceService.fetchHistoricalData(symbol);
            
            Platform.runLater(() -> {
                window.updatePrice(currentPrice);
                window.updateChart(historicalPrices);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleUpdates(StockPriceWindow window) {
        updateTask = scheduler.scheduleAtFixedRate(() -> {
            updateStockData(window);
        }, UPDATE_INTERVAL, UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}