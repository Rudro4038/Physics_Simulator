package com.physicssim.features.pendulum;

import com.physicssim.theme.AppTheme;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PendulumSimulationView extends BorderPane {

    private static final double DEFAULT_ANGLE_DEGREES = 30;
    private static final int HISTORY_LIMIT = 120;

    private final PendulumModel model = new PendulumModel(2.0, 9.81, 1.5, Math.toRadians(DEFAULT_ANGLE_DEGREES));
    private final PendulumCanvas canvas = new PendulumCanvas(model);
    private final PendulumControlPanel controlPanel;
    private final PendulumChartCard angleChart = new PendulumChartCard(
            "Angular Displacement vs. Time",
            "Angle (deg)",
            "Time");
    private final PendulumChartCard velocityChart = new PendulumChartCard(
            "Velocity vs. Time",
            "Speed (m/s)",
            "Time");
    private final Label periodValueLabel = metricValueLabel();
    private final Label maxVelocityValueLabel = metricValueLabel();
    private final Label currentVelocityValueLabel = metricValueLabel();
    private final Label positionValueLabel = metricValueLabel();
    private final Label statusLabel = new Label("Paused");
    private final List<Double> angleHistory = new ArrayList<>();
    private final List<Double> velocityHistory = new ArrayList<>();
    private boolean running = true;
    private double maxVelocity = 0;

    public PendulumSimulationView() {
        setPadding(new Insets(18));
        setStyle("-fx-background-color: transparent;");

        controlPanel = new PendulumControlPanel(
                this::toggleRunning,
                this::resetSimulation,
                this::updateAngleFromSlider,
                this::updateGravityFromSlider,
                this::updateLengthFromSlider,
                this::updateMassFromSlider);

        Pane simulationFrame = PendulumSimulationLayout.wrapSimulation(canvas);
        VBox metrics = buildMetricsColumn();

        PendulumSimulationLayout.setMainRow(this, controlPanel, simulationFrame, metrics);

        HBox bottomRow = new HBox(24, angleChart, velocityChart);
        bottomRow.setAlignment(Pos.CENTER_LEFT);
        bottomRow.setPadding(new Insets(16, 0, 0, 0));

        VBox root = new VBox(bottomRow);
        setBottom(root);

        resetTelemetry();
        updateReadings();
        startAnimation();
    }

    private VBox buildMetricsColumn() {
        Label title = new Label("Live metrics");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: 800; -fx-text-fill: #1e1b4b; -fx-background-color: linear-gradient(to right, #f0abfc, #c084fc); -fx-background-radius: 8; -fx-padding: 6 12; -fx-background-insets: 0 4 0 0;");

        statusLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: 700; -fx-text-fill: #6b7280;");

        positionValueLabel.setWrapText(true);

        return new VBox(12,
                title,
                statusLabel,
                statBlock("Period", periodValueLabel),
                statBlock("Max velocity", maxVelocityValueLabel),
                statBlock("Current velocity", currentVelocityValueLabel),
                statBlock("Bob position", positionValueLabel));
    }

    private VBox statBlock(String name, Label valueLabel) {
        Label label = new Label(name);
        label.setFont(AppTheme.cardTitleFont());
        label.setStyle("-fx-font-size: 13px; -fx-font-weight: 800; -fx-text-fill: #0369a1;");
        return new VBox(4, label, valueLabel);
    }

    private Label metricValueLabel() {
        Label label = new Label();
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: 900; -fx-text-fill: #7c3aed; -fx-effect: dropshadow(gaussian, rgba(124, 58, 237, 0.2), 4, 0.15, 0, 2);");
        return label;
    }

    private void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastFrameTime = -1;

            @Override
            public void handle(long now) {
                if (lastFrameTime < 0) {
                    lastFrameTime = now;
                    return;
                }

                double deltaSeconds = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;

                if (running) {
                    model.update(Math.min(deltaSeconds, 0.03));
                    canvas.render();
                    updateReadings();
                }
            }
        };
        timer.start();
    }

    private void toggleRunning() {
        running = !running;
        controlPanel.setPlayPauseButton(running);
        updateReadings();
    }

    private void resetSimulation() {
        model.reset(Math.toRadians(controlPanel.getSelectedAngle()));
        canvas.clearTrail();
        canvas.render();
        resetTelemetry();
        running = false;
        controlPanel.setPlayPauseButton(false);
        updateReadings();
    }

    private void updateAngleFromSlider(double angleDegrees) {
        model.reset(Math.toRadians(angleDegrees));
        canvas.clearTrail();
        canvas.render();
        resetTelemetry();
        updateReadings();
    }

    private void updateGravityFromSlider(double gravity) {
        model.setGravity(gravity);
        updateReadings();
    }

    private void updateLengthFromSlider(double lengthMeters) {
        model.setLengthMeters(lengthMeters);
        canvas.clearTrail();
        canvas.render();
        updateReadings();
    }

    private void updateMassFromSlider(double bobMass) {
        model.setBobMass(bobMass);
        canvas.render();
        updateReadings();
    }

    private void updateReadings() {
        double angleDegrees = Math.toDegrees(model.getAngle());
        double currentVelocity = model.getLinearSpeed();
        maxVelocity = Math.max(maxVelocity, currentVelocity);

        pushHistory(angleHistory, angleDegrees);
        pushHistory(velocityHistory, currentVelocity);

        periodValueLabel.setText(String.format("%.2f s", model.getPeriod()));
        maxVelocityValueLabel.setText(String.format("%.2f m/s", maxVelocity));
        currentVelocityValueLabel.setText(String.format("%.2f m/s", currentVelocity));
        positionValueLabel.setText(String.format("x=%.2f m, y=%.2f m", model.getHorizontalPosition(), model.getVerticalPosition()));

        statusLabel.setText(running ? "Status: running" : "Status: paused");

        angleChart.plot(angleHistory, javafx.scene.paint.Color.web("#22c55e"));
        velocityChart.plot(velocityHistory, javafx.scene.paint.Color.web("#3b82f6"));
    }

    private void pushHistory(List<Double> history, double value) {
        history.add(value);
        if (history.size() > HISTORY_LIMIT) {
            history.remove(0);
        }
    }

    private void resetTelemetry() {
        angleHistory.clear();
        velocityHistory.clear();
        maxVelocity = 0;
    }
}
