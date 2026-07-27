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

public class VectorParallelogramView extends BorderPane {

    private final boolean subtractionMode;
    private final Canvas vectorCanvas = new Canvas(520, 300);
    private final Slider firstMagnitude = new Slider(1, 8, 4);
    private final Slider firstAngle = new Slider(0, 360, 35);
    private final Slider secondMagnitude = new Slider(1, 8, 3);
    private final Slider secondAngle = new Slider(0, 360, 120);
    private final Label resultLabel = new Label();
    private final Label directionLabel = new Label();

    public VectorParallelogramView(boolean subtractionMode) {
        this.subtractionMode = subtractionMode;
        setPadding(new Insets(18));
        setStyle("-fx-background-color: transparent;");

        Label heading = new Label(subtractionMode ? "Vector subtraction" : "Vector addition");
        heading.setFont(AppTheme.cardTitleFont());
        heading.setStyle(VectorSimulationLayout.toolHeadingStyle());

        Label intro = new Label(subtractionMode
                ? "Subtract Vector 2 from Vector 1 using the parallelogram law."
                : "Combine two vectors and view the resultant using the parallelogram law.");
        intro.setWrapText(true);
        intro.setStyle("-fx-font-size: 12px; -fx-text-fill: #6b7280; -fx-font-weight: 600;");

        resultLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700; -fx-text-fill: #075985;");
        resultLabel.setWrapText(true);
        directionLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700; -fx-text-fill: #075985;");
        directionLabel.setWrapText(true);

        VBox controls = new VBox(10,
                heading,
                intro,
                buildSliderRow("Vector 1 magnitude", firstMagnitude),
                buildSliderRow("Vector 1 angle (°)", firstAngle),
                buildSliderRow("Vector 2 magnitude", secondMagnitude),
                buildSliderRow("Vector 2 angle (°)", secondAngle),
                resultLabel,
                directionLabel);
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

        double arrowX2 = subtractionMode ? -x2 : x2;
        double arrowY2 = subtractionMode ? -y2 : y2;
        double rx = x1 + arrowX2;
        double ry = y1 + arrowY2;
        double magnitude = Math.hypot(rx, ry);
        double directionDegrees = Math.toDegrees(Math.atan2(ry, rx));

        double mag1 = Math.hypot(x1, y1);
        double mag2 = Math.hypot(arrowX2, arrowY2);
        double betweenAngle = 0;
        if (mag1 > 0 && mag2 > 0) {
            double dot = x1 * arrowX2 + y1 * arrowY2;
            betweenAngle = Math.toDegrees(Math.acos(Math.max(-1, Math.min(1, dot / (mag1 * mag2)))));
        }

        String operation = subtractionMode ? "Subtraction result" : "Addition result";
        String betweenLabel = subtractionMode ? "Angle V1 to -V2" : "Angle V1 to V2";

        resultLabel.setText(String.format("%s: %.2f   Resultant: (%.2f, %.2f)", operation, magnitude, rx, ry));
        directionLabel.setText(String.format(
                "Direction: %.2f° from +x   %s: %.2f°",
                directionDegrees, betweenLabel, betweenAngle));
        drawDiagram(x1, y1, x2, y2, rx, ry, directionDegrees);
    }

    private void drawDiagram(double x1, double y1, double x2, double y2, double rx, double ry, double directionDegrees) {
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
        double arrowX2 = subtractionMode ? -x2 : x2;
        double arrowY2 = subtractionMode ? -y2 : y2;
        double secondArrowEndX = originX + arrowX2 * 35;
        double secondArrowEndY = originY - arrowY2 * 35;
        double resultantEndX = originX + rx * 35;
        double resultantEndY = originY - ry * 35;

        gc.setStroke(Color.web("#3157d5"));
        gc.setLineWidth(3);
        gc.strokeLine(originX, originY, firstEndX, firstEndY);
        gc.setFill(Color.web("#3157d5"));
        gc.fillOval(firstEndX - 5, firstEndY - 5, 10, 10);

        gc.setStroke(Color.web("#0ea5a4"));
        gc.setLineWidth(3);
        gc.strokeLine(originX, originY, secondArrowEndX, secondArrowEndY);
        gc.setFill(Color.web("#0ea5a4"));
        gc.fillOval(secondArrowEndX - 5, secondArrowEndY - 5, 10, 10);

        gc.setStroke(Color.web("#f97316"));
        gc.setLineWidth(3);
        gc.strokeLine(originX, originY, resultantEndX, resultantEndY);
        gc.setFill(Color.web("#f97316"));
        gc.fillOval(resultantEndX - 5, resultantEndY - 5, 10, 10);

        gc.setStroke(Color.web("#9ca3af"));
        gc.setLineWidth(1.5);
        gc.strokeLine(firstEndX, firstEndY, firstEndX + arrowX2 * 35, firstEndY - arrowY2 * 35);
        gc.strokeLine(secondArrowEndX, secondArrowEndY, secondArrowEndX + x1 * 35, secondArrowEndY - y1 * 35);

        gc.setFill(Color.web("#111827"));
        gc.fillText("Vector 1", firstEndX + 8, firstEndY - 8);
        gc.fillText(subtractionMode ? "-Vector 2" : "Vector 2", secondArrowEndX + 8, secondArrowEndY - 8);
        gc.fillText("Resultant", resultantEndX + 8, resultantEndY - 8);
        gc.fillText(String.format("Direction: %.2f°", directionDegrees), originX + 8, originY - 8);
    }
}
