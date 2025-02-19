package com.stocktracker.ui;

import java.util.List;
import java.util.Map;

import com.stocktracker.model.StockPrice;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StockPriceWindow {
    private final Stage stage;
    private final Label priceLabel;
    private final Label timestampLabel;
    private final ComboBox<String> symbolComboBox;
    private final StockChartComponent chartComponent;
    private final Map<String, String> stockNames = Map.of(
        "AAPL", "Apple Inc.",
        "MSFT", "Microsoft Corporation",
        "TSLA", "Tesla, Inc.",
        "AMZN", "Amazon.com, Inc.",
        "GOOGL", "Alphabet Inc. (Google)"
    );

    public StockPriceWindow() {
        stage = new Stage();
        stage.setTitle("Stock Price Tracker");
        stage.setAlwaysOnTop(true);

        // Create ComboBox with stock symbols
        symbolComboBox = new ComboBox<>();
        stockNames.forEach((symbol, name) -> 
            symbolComboBox.getItems().add(String.format("%s - %s", symbol, name))
        );
        symbolComboBox.getSelectionModel().selectFirst();
        symbolComboBox.setStyle("-fx-font-size: 14px;");

        priceLabel = new Label();
        priceLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        timestampLabel = new Label();
        timestampLabel.setStyle("-fx-font-size: 12px;");

        // Create chart component
        chartComponent = new StockChartComponent();

        // Create main layout
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(15));
        VBox.setVgrow(chartComponent, Priority.ALWAYS);
        root.getChildren().addAll(symbolComboBox, priceLabel, timestampLabel, chartComponent);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    public String getSelectedSymbol() {
        String selected = symbolComboBox.getValue();
        return selected.substring(0, selected.indexOf(" "));
    }

    public void setOnSymbolChanged(Runnable callback) {
        symbolComboBox.setOnAction(e -> callback.run());
    }

    public void updatePrice(StockPrice stockPrice) {
        priceLabel.setText(String.format("$%.2f", stockPrice.getPrice()));
        timestampLabel.setText("Last updated: " + stockPrice.getTimestamp());
    }

    public void updateChart(List<StockPrice> prices) {
        chartComponent.updateChart(prices, getSelectedSymbol());
    }

    public void show() {
        stage.show();
    }
}