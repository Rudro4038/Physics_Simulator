package com.physicssim.features.pendulum;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PendulumChartCard extends VBox {

    private final Canvas canvas = new Canvas(260, 130);

    public PendulumChartCard(String title) {
        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 700;");

        getChildren().addAll(titleLabel, canvas);
        setSpacing(12);
        setPadding(new Insets(18));
        setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(18), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(
                Color.web("#d9e2ee"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(18),
                new BorderWidths(1))));
        setStyle("-fx-effect: dropshadow(gaussian, rgba(15, 23, 32, 0.08), 16, 0.18, 0, 6);");
    }

    public void plot(List<Double> values, Color lineColor) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        gc.setFill(Color.web("#f8fbff"));
        gc.fillRoundRect(0, 0, width, height, 14, 14);

        gc.setStroke(Color.web("#dce6f2"));
        for (int x = 0; x <= width; x += 30) {
            gc.strokeLine(x, 0, x, height);
        }
        for (int y = 0; y <= height; y += 26) {
            gc.strokeLine(0, y, width, y);
        }

        if (values.size() < 2) {
            return;
        }

        double min = values.stream().mapToDouble(Double::doubleValue).min().orElse(-1);
        double max = values.stream().mapToDouble(Double::doubleValue).max().orElse(1);
        double range = Math.max(max - min, 1e-6);

        gc.setStroke(lineColor);
        gc.setLineWidth(2.5);
        for (int index = 1; index < values.size(); index++) {
            double x1 = ((index - 1) / (double) (values.size() - 1)) * width;
            double x2 = (index / (double) (values.size() - 1)) * width;
            double y1 = height - ((values.get(index - 1) - min) / range) * height;
            double y2 = height - ((values.get(index) - min) / range) * height;
            gc.strokeLine(x1, y1, x2, y2);
        }
    }
}
