package com.stocktracker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.stocktracker.service.StockPriceService;
import com.stocktracker.ui.StockPriceWindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class StockTrackerApp extends Application {
    private static final String SYMBOL = "AAPL";
    private static final int UPDATE_INTERVAL = 60; // seconds
    private final StockPriceService stockPriceService;
    private final ScheduledExecutorService scheduler;

    public StockTrackerApp() {
        this.stockPriceService = new StockPriceService();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void start(Stage primaryStage) {
        StockPriceWindow window = new StockPriceWindow();
        window.show();

        // Schedule periodic updates
        scheduler.scheduleAtFixedRate(() -> {
            try {
                var stockPrice = stockPriceService.fetchStockPrice(SYMBOL);
                Platform.runLater(() -> window.updatePrice(stockPrice));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}