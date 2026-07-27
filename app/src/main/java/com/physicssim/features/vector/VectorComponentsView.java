package com.physicssim.features.vector;

import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VectorComponentsView extends BorderPane {

    private final Canvas vectorCanvas = new Canvas(520, 300);
    private final Slider magnitudeSlider = new Slider(1, 10, 5);
    private final Slider angleSlider = new Slider(0, 360, 45);
    private final Label magnitudeValue = new Label();
    private final Label componentValue = new Label();

    public VectorComponentsView() {
        setPadding(new Insets(18));
        setStyle("-fx-background-color: transparent;");

        Label heading = new Label("Vector components");
        heading.setFont(AppTheme.cardTitleFont());
        heading.setStyle(VectorSimulationLayout.toolHeadingStyle());

        Label intro = new Label(
                "A vector is defined by magnitude and direction. Components are found by projecting onto the axes.");
        intro.setWrapText(true);
        intro.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: 600;");

        magnitudeSlider.setShowTickLabels(true);
        magnitudeSlider.setShowTickMarks(true);
        magnitudeSlider.setMajorTickUnit(1);

        angleSlider.setShowTickLabels(true);
        angleSlider.setShowTickMarks(true);
        angleSlider.setMajorTickUnit(60);

        magnitudeValue.setStyle("-fx-font-size: 14px; -fx-font-weight: 700; -fx-text-fill: #075985;");
        componentValue.setStyle("-fx-font-size: 14px; -fx-font-weight: 700; -fx-text-fill: #075985;");

        VBox controls = new VBox(12,
                heading,
                intro,
                new Label("Magnitude"),
                magnitudeSlider,
                new Label("Angle (degrees)"),
                angleSlider,
                magnitudeValue,
                componentValue);
        VectorSimulationLayout.setCanvasAndControls(this, vectorCanvas, controls);

        magnitudeSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateVisualization());
        angleSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateVisualization());
        updateVisualization();
    }

    private void updateVisualization() {
        double magnitude = magnitudeSlider.getValue();
        double angleDegrees = angleSlider.getValue();
        double angleRadians = Math.toRadians(angleDegrees);

        double vx = magnitude * Math.cos(angleRadians);
        double vy = magnitude * Math.sin(angleRadians);

        magnitudeValue.setText(String.format("Magnitude: %.2f", magnitude));
        componentValue.setText(String.format("Components: (%.2f, %.2f)", vx, vy));
        drawVector(vx, vy);
    }

    private void drawVector(double vx, double vy) {
        GraphicsContext gc = vectorCanvas.getGraphicsContext2D();
        double width = vectorCanvas.getWidth();
        double height = vectorCanvas.getHeight();

        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.web("#f8fafc"));
        gc.fillRoundRect(8, 8, width - 16, height - 16, 12, 12);

        gc.setStroke(Color.web("#d0d7de"));
        gc.setLineWidth(1);
        gc.strokeLine(20, height / 2, width - 20, height / 2);
        gc.strokeLine(width / 2, 20, width / 2, height - 20);

        gc.setStroke(Color.web("#3157d5"));
        gc.setLineWidth(3);
        gc.strokeLine(width / 2, height / 2, width / 2 + vx * 30, height / 2 - vy * 30);

        gc.setFill(Color.web("#3157d5"));
        gc.fillOval(width / 2 + vx * 30 - 5, height / 2 - vy * 30 - 5, 10, 10);

        gc.setFill(Color.web("#1f2937"));
        gc.fillText("x", width - 30, height / 2 - 8);
        gc.fillText("y", width / 2 + 8, 20);
        gc.fillText("vector", width / 2 + vx * 30 + 8, height / 2 - vy * 30 - 8);
    }
}
