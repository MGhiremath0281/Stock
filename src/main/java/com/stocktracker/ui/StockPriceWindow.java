package com.stocktracker.ui;

import com.stocktracker.model.StockPrice;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Map;

public class StockPriceWindow {
    private final Stage stage;
    private final Label priceLabel;
    private final Label timestampLabel;
    private final ComboBox<String> symbolComboBox;
    private final Map<String, String> stockNames = Map.of(
        "AAPL", "Apple Inc.",
        "MSFT", "Microsoft Corporation",
        "TSLA", "Tesla, Inc.",
        "AMZN", "Amazon.com, Inc.",
        "GOOGL", "Alphabet Inc. (Google)"
    );

    public StockPriceWindow() {
        stage = new Stage(StageStyle.UTILITY);
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

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15));
        root.getChildren().addAll(symbolComboBox, priceLabel, timestampLabel);

        Scene scene = new Scene(root, 300, 150);
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

    public void show() {
        stage.show();
    }
}