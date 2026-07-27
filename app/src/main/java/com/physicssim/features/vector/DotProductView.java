package com.physicssim.features.vector;

import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DotProductView extends BorderPane {

    private final Canvas vectorCanvas = new Canvas(520, 300);
    private final Slider firstMagnitude = new Slider(1, 8, 4);
    private final Slider firstAngle = new Slider(0, 360, 35);
    private final Slider secondMagnitude = new Slider(1, 8, 3);
    private final Slider secondAngle = new Slider(0, 360, 120);
    private final Label resultLabel = new Label();
    private final Label angleLabel = new Label();

    public DotProductView() {
        setPadding(new Insets(18));
        setStyle("-fx-background-color: transparent;");

        Label heading = new Label("Dot product");
        heading.setFont(AppTheme.cardTitleFont());
        heading.setStyle(VectorSimulationLayout.toolHeadingStyle());

        Label intro = new Label("Compute the scalar product of two vectors and explore the angle between them.");
        intro.setWrapText(true);
        intro.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: 600;");

        resultLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700; -fx-text-fill: #075985;");
        angleLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700; -fx-text-fill: #075985;");

        VBox controls = new VBox(10,
                heading,
                intro,
                buildSliderRow("Vector 1 magnitude", firstMagnitude),
                buildSliderRow("Vector 1 angle (°)", firstAngle),
                buildSliderRow("Vector 2 magnitude", secondMagnitude),
                buildSliderRow("Vector 2 angle (°)", secondAngle),
                resultLabel,
                angleLabel);
        VectorSimulationLayout.setCanvasAndControls(this, vectorCanvas, controls);

        bindListeners();
        updateDiagram();
    }

    private VBox buildSliderRow(String labelText, Slider slider) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 13px; -fx-font-weight: 800; -fx-text-fill: #0369a1;");
        Label valueLabel = new Label(String.format("%.0f", slider.getValue()));
        valueLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700; -fx-text-fill: #075985;");
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((obs, oldValue, newValue) ->
                valueLabel.setText(String.format("%.0f", newValue.doubleValue())));
        return new VBox(4, label, slider, valueLabel);
    }

    private void bindListeners() {
        firstMagnitude.valueProperty().addListener((obs, oldValue, newValue) -> updateDiagram());
        firstAngle.valueProperty().addListener((obs, oldValue, newValue) -> updateDiagram());
        secondMagnitude.valueProperty().addListener((obs, oldValue, newValue) -> updateDiagram());
        secondAngle.valueProperty().addListener((obs, oldValue, newValue) -> updateDiagram());
    }

    private void updateDiagram() {
        double x1 = firstMagnitude.getValue() * Math.cos(Math.toRadians(firstAngle.getValue()));
        double y1 = firstMagnitude.getValue() * Math.sin(Math.toRadians(firstAngle.getValue()));
        double x2 = secondMagnitude.getValue() * Math.cos(Math.toRadians(secondAngle.getValue()));
        double y2 = secondMagnitude.getValue() * Math.sin(Math.toRadians(secondAngle.getValue()));

        double mag1 = Math.hypot(x1, y1);
        double mag2 = Math.hypot(x2, y2);
        double rawAngle = Math.abs(firstAngle.getValue() - secondAngle.getValue()) % 360;
        if (rawAngle > 180) {
            rawAngle = 360 - rawAngle;
        }
        double angle = mag1 > 0 && mag2 > 0 ? rawAngle : 0;
        double dot = mag1 > 0 && mag2 > 0 ? mag1 * mag2 * Math.cos(Math.toRadians(angle)) : 0;
        if (Math.abs(dot) < 1e-6) {
            dot = 0;
        }

        resultLabel.setText(String.format("Dot product: %.2f", dot));
        angleLabel.setText(String.format("Angle between: %.2f degrees", angle));
        drawDiagram(x1, y1, x2, y2, dot);
    }

    private void drawDiagram(double x1, double y1, double x2, double y2, double dot) {
        GraphicsContext gc = vectorCanvas.getGraphicsContext2D();
        double width = vectorCanvas.getWidth();
        double height = vectorCanvas.getHeight();

        gc.clearRect(0, 0, width, height);
        gc.setFill(Color.web("#f8fafc"));
        gc.fillRoundRect(8, 8, width - 16, height - 16, 12, 12);

        gc.setStroke(Color.web("#d9e2ee"));
        gc.setLineWidth(1);
        gc.strokeLine(30, height / 2, width - 30, height / 2);
        gc.strokeLine(width / 2, 30, width / 2, height - 30);

        double originX = width / 2;
        double originY = height / 2;
        double firstEndX = originX + x1 * 35;
        double firstEndY = originY - y1 * 35;
        double secondEndX = originX + x2 * 35;
        double secondEndY = originY - y2 * 35;

        gc.setStroke(Color.web("#3157d5"));
        gc.setLineWidth(3);
        gc.strokeLine(originX, originY, firstEndX, firstEndY);
        gc.setFill(Color.web("#3157d5"));
        gc.fillOval(firstEndX - 5, firstEndY - 5, 10, 10);

        gc.setStroke(Color.web("#0ea5a4"));
        gc.setLineWidth(3);
        gc.strokeLine(originX, originY, secondEndX, secondEndY);
        gc.setFill(Color.web("#0ea5a4"));
        gc.fillOval(secondEndX - 5, secondEndY - 5, 10, 10);

        gc.setStroke(Color.web("#818cf8"));
        gc.setLineWidth(2);
        gc.strokeLine(firstEndX, firstEndY, secondEndX, secondEndY);

        gc.setFill(Color.web("#111827"));
        gc.fillText("Vector 1", firstEndX + 8, firstEndY - 8);
        gc.fillText("Vector 2", secondEndX + 8, secondEndY - 8);
        gc.fillText("Dot = " + String.format("%.2f", dot), originX + 8, originY - 8);
    }
}
