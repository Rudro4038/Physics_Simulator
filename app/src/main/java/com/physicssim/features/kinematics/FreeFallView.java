package com.physicssim.features.kinematics;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FreeFallView extends BorderPane {

    private final Canvas canvas = new Canvas(600, 350);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private final Slider heightSlider = new Slider(5, 100, 40);
    private final Label heightLabel = new Label("40.0 m");
    private final Label timeLabel = new Label("0.00 s");
    private final Label velocityLabel = new Label("0.0 m/s");
    private final Button dropBtn = new Button("Drop");
    private final Button resetBtn = new Button("Reset");
    private final Button heightUpBtn = new Button("Height");
    private final Button heightDownBtn = new Button("Height");

    private boolean isFalling = false;
    private double currentHeight = 0;
    private double elapsedTime = 0;
    private double maxHeight = 40;
    private double pixelsPerMeter = 2.5;
    private AnimationTimer animationTimer;

    public FreeFallView() {
        setPadding(new Insets(18));
        setStyle("-fx-background-color: transparent;");

        Pane canvasContainer = new Pane(canvas);
        canvasContainer.setStyle("-fx-background-color: linear-gradient(135deg, #fa709a 0%, #fee140 100%); -fx-background-radius: 20; -fx-border-color: #e11d48; -fx-border-radius: 20; -fx-effect: dropshadow(gaussian, rgba(250, 112, 154, 0.4), 20, 0.25, 0, 8);");

        heightSlider.valueProperty().addListener((obs, old, newVal) -> {
            heightLabel.setText(String.format("%.1f m", newVal.doubleValue()));
            if (!isFalling) {
                maxHeight = newVal.doubleValue();
                currentHeight = newVal.doubleValue();
                drawCanvas();
            }
        });

        dropBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: 800; -fx-background-color: linear-gradient(to right, #10b981, #059669); -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 12 20; -fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.3), 8, 0.2, 0, 4);");
        resetBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: 800; -fx-background-color: linear-gradient(to right, #ef4444, #dc2626); -fx-text-fill: white; -fx-background-radius: 12; -fx-padding: 12 20; -fx-effect: dropshadow(gaussian, rgba(239, 68, 68, 0.3), 8, 0.2, 0, 4);");
        heightUpBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: 800; -fx-background-color: linear-gradient(to right, #8b5cf6, #7c3aed); -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10 14; -fx-min-width: 55px; -fx-effect: dropshadow(gaussian, rgba(139, 92, 246, 0.3), 6, 0.18, 0, 3);");
        heightDownBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: 800; -fx-background-color: linear-gradient(to right, #8b5cf6, #7c3aed); -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10 14; -fx-min-width: 55px; -fx-effect: dropshadow(gaussian, rgba(139, 92, 246, 0.3), 6, 0.18, 0, 3);");

        HBox heightControlButtons = new HBox(10, heightDownBtn, heightUpBtn);
        heightControlButtons.setAlignment(Pos.CENTER);

        VBox controls = new VBox(14,
                statBlock("Initial Height", heightLabel),
                heightSlider,
                heightControlButtons,
                new HBox(10, dropBtn, resetBtn),
                statBlock("Elapsed Time", timeLabel),
                statBlock("Current Velocity", velocityLabel));
        controls.setAlignment(Pos.TOP_LEFT);
        controls.setPadding(new Insets(8));

        HBox body = new HBox(24, canvasContainer, controls);
        body.setAlignment(Pos.TOP_LEFT);

        setCenter(body);
        setupAnimation();
        drawCanvas();
    }

    private void setupAnimation() {
        heightUpBtn.setOnAction(event -> {
            if (!isFalling) {
                double newValue = Math.min(heightSlider.getMax(), heightSlider.getValue() + 5);
                heightSlider.setValue(newValue);
            }
        });

        heightDownBtn.setOnAction(event -> {
            if (!isFalling) {
                double newValue = Math.max(heightSlider.getMin(), heightSlider.getValue() - 5);
                heightSlider.setValue(newValue);
            }
        });

        dropBtn.setOnAction(event -> {
            if (!isFalling && currentHeight > 0) {
                isFalling = true;
                dropBtn.setText("Pause");
            } else if (isFalling) {
                isFalling = false;
                dropBtn.setText("Drop");
            }
        });

        resetBtn.setOnAction(event -> {
            isFalling = false;
            dropBtn.setText("Drop");
            maxHeight = heightSlider.getValue();
            currentHeight = maxHeight;
            elapsedTime = 0;
            timeLabel.setText("0.00 s");
            velocityLabel.setText("0.0 m/s");
            drawCanvas();
        });

        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1e9;
                lastUpdate = now;

                if (isFalling) {
                    elapsedTime += deltaTime;
                    currentHeight = maxHeight - 0.5 * 9.81 * elapsedTime * elapsedTime;
                    double v = 9.81 * elapsedTime;

                    if (currentHeight <= 0) {
                        currentHeight = 0;
                        isFalling = false;
                        dropBtn.setText("Drop");
                    }

                    timeLabel.setText(String.format("%.2f s", elapsedTime));
                    velocityLabel.setText(String.format("%.1f m/s", v));
                    drawCanvas();
                }
            }
        };
        animationTimer.start();
    }

    private void drawCanvas() {
        gc.setFill(Color.web("#e0f2fe"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.web("#6b7280"));
        gc.setLineWidth(4);
        gc.strokeLine(40, 310, 560, 310);

        gc.setStroke(Color.web("#94a3b8"));
        gc.setLineWidth(2);
        gc.strokeLine(50, 310 - maxHeight * pixelsPerMeter, 60, 310 - maxHeight * pixelsPerMeter);
        gc.strokeLine(50, 310, 60, 310);
        gc.setFill(Color.BLACK);
        gc.setFont(javafx.scene.text.Font.font(12));
        gc.fillText(String.format("%.0f m", maxHeight), 65, 310 - maxHeight * pixelsPerMeter + 4);

        gc.setFill(Color.web("#2563eb"));
        double yPos = 310 - currentHeight * pixelsPerMeter - 20;
        gc.fillOval(280, yPos, 20, 20);

        // Show velocity, time, and height while falling
        if (isFalling) {
            gc.setFill(Color.web("#dc2626"));
            gc.setFont(javafx.scene.text.Font.font(14));
            double v = 9.81 * elapsedTime;
            double heightCovered = maxHeight - currentHeight;
            gc.fillText(String.format("v = %.1f m/s", v), 310, 310 - currentHeight * pixelsPerMeter - 45);
            gc.fillText(String.format("t = %.2f s", elapsedTime), 310, 310 - currentHeight * pixelsPerMeter - 30);
            gc.fillText(String.format("h = %.2f m", heightCovered), 310, 310 - currentHeight * pixelsPerMeter - 15);
        }
    }

    private VBox statBlock(String name, Label value) {
        Label label = new Label(name);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: 800; -fx-text-fill: #881337; -fx-background-color: linear-gradient(to right, #fbcfe8, #f9a8d4); -fx-background-radius: 8; -fx-padding: 6 12; -fx-background-insets: 0 4 0 0;");
        value.setStyle("-fx-font-size: 18px; -fx-font-weight: 900; -fx-text-fill: #be185d; -fx-effect: dropshadow(gaussian, rgba(190, 24, 93, 0.2), 4, 0.15, 0, 2);");
        return new VBox(8, label, value);
    }
}