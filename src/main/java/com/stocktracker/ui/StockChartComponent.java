package com.stocktracker.ui;

import java.time.ZoneOffset;
import java.util.List;

import com.stocktracker.model.StockPrice;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class StockChartComponent extends VBox {
    private final LineChart<Number, Number> lineChart;
    private final XYChart.Series<Number, Number> series;

    public StockChartComponent() {
        // Create chart axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Price ($)");

        // Create line chart
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Price History");
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);

        // Create data series
        series = new XYChart.Series<>();
        lineChart.getData().add(series);

        // Style the chart
        lineChart.setStyle("-fx-background-color: white;");
        VBox.setVgrow(lineChart, Priority.ALWAYS);

        getChildren().add(lineChart);
    }

    public void updateChart(List<StockPrice> prices, String symbol) {
        series.getData().clear();
        series.setName(symbol);

        for (StockPrice price : prices) {
            // Convert LocalDateTime to epoch seconds for X axis
            long epochSeconds = price.getTimestamp().toEpochSecond(ZoneOffset.UTC);
            series.getData().add(new XYChart.Data<>(epochSeconds, price.getPrice()));
        }
    }
}