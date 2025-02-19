package com.stocktracker.ui;

import com.stocktracker.model.StockPrice;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockPriceWindow {
    private final Stage stage;
    private final Label priceLabel;
    private final Label timestampLabel;

    public StockPriceWindow() {
        stage = new Stage(StageStyle.UTILITY);
        stage.setTitle("AAPL Stock Price");
        stage.setAlwaysOnTop(true);

        priceLabel = new Label();
        priceLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        timestampLabel = new Label();
        timestampLabel.setStyle("-fx-font-size: 12px;");

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(priceLabel, timestampLabel);

        Scene scene = new Scene(root, 200, 100);
        stage.setScene(scene);
    }

    public void updatePrice(StockPrice stockPrice) {
        priceLabel.setText(String.format("$%.2f", stockPrice.getPrice()));
        timestampLabel.setText("Last updated: " + stockPrice.getTimestamp());
    }

    public void show() {
        stage.show();
    }
}