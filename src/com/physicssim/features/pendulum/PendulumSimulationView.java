package com.physicssim.features.pendulum;

import com.physicssim.theme.AppTheme;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PendulumSimulationView extends BorderPane {

    private static final double DEFAULT_ANGLE_DEGREES = 30;
    private static final int HISTORY_LIMIT = 120;

    private final PendulumModel model = new PendulumModel(2.0, 9.81, 1.5, Math.toRadians(DEFAULT_ANGLE_DEGREES));
    private final PendulumCanvas canvas = new PendulumCanvas(model);
    private final PendulumControlPanel controlPanel;
    private final PendulumChartCard angleChart = new PendulumChartCard("Angular Displacement vs. Time");
    private final PendulumChartCard velocityChart = new PendulumChartCard("Velocity vs. Time");
    private final Label periodValueLabel = metricValueLabel();
    private final Label maxVelocityValueLabel = metricValueLabel();
    private final Label currentVelocityValueLabel = metricValueLabel();
    private final Label positionValueLabel = metricValueLabel();
    private final List<Double> angleHistory = new ArrayList<>();
    private final List<Double> velocityHistory = new ArrayList<>();
    private boolean running = true;
    private double maxVelocity = 0;

    public PendulumSimulationView() {
        Label title = new Label("Pendulum Dynamics Dashboard");
        title.setFont(AppTheme.heroFont());
        title.setTextFill(Color.BLACK);

        Label subtitle = new Label("Interactive pendulum lab with live controls, simulation space, and motion analytics.");
        subtitle.setFont(AppTheme.subtitleFont());
        subtitle.setTextFill(Color.BLACK);
        subtitle.setWrapText(true);

        controlPanel = new PendulumControlPanel(
                this::toggleRunning,
                this::resetSimulation,
                this::updateAngleFromSlider,
                this::updateGravityFromSlider,
                this::updateLengthFromSlider,
                this::updateMassFromSlider);

        VBox textBlock = new VBox(10, title, subtitle);
        textBlock.setAlignment(Pos.TOP_LEFT);

        BorderPane simulationBoard = new BorderPane(canvas);
        simulationBoard.setPadding(new Insets(18));
        simulationBoard.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(18), Insets.EMPTY)));
        simulationBoard.setStyle("-fx-border-color: #d9e2ee;"
                + "-fx-border-radius: 18;"
                + "-fx-effect: dropshadow(gaussian, rgba(15, 23, 32, 0.08), 18, 0.18, 0, 6);");

        HBox topRow = new HBox(20, controlPanel, simulationBoard);
        HBox.setHgrow(simulationBoard, Priority.ALWAYS);
        topRow.setAlignment(Pos.TOP_LEFT);

        VBox metricsCard = buildMetricsCard();
        HBox bottomRow = new HBox(20, metricsCard, angleChart, velocityChart);
        HBox.setHgrow(angleChart, Priority.ALWAYS);
        HBox.setHgrow(velocityChart, Priority.ALWAYS);

        VBox content = new VBox(24, textBlock, topRow, bottomRow);
        content.setPadding(new Insets(30));
        content.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(24), Insets.EMPTY)));
        content.setStyle("-fx-border-color: #dde5ef;"
                + "-fx-border-radius: 24;"
                + "-fx-effect: dropshadow(gaussian, rgba(16, 24, 40, 0.08), 24, 0.20, 0, 8);");
        content.setMaxWidth(1180);

        setPadding(new Insets(8, 0, 14, 0));
        setCenter(content);

        resetTelemetry();
        updateReadings();
        startAnimation();
    }

    private VBox buildMetricsCard() {
        Label title = new Label("Live Metrics");
        title.setTextFill(Color.BLACK);
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: 700;");

        VBox card = new VBox(12,
                title,
                metricRow("Period", periodValueLabel),
                metricRow("Max Velocity", maxVelocityValueLabel),
                metricRow("Current Velocity", currentVelocityValueLabel),
                metricRow("Position", positionValueLabel));
        card.setPadding(new Insets(18));
        card.setPrefWidth(240);
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(18), Insets.EMPTY)));
        card.setStyle("-fx-border-color: #d9e2ee;"
                + "-fx-border-radius: 18;"
                + "-fx-effect: dropshadow(gaussian, rgba(15, 23, 32, 0.08), 16, 0.18, 0, 6);");
        return card;
    }

    private HBox metricRow(String name, Label valueLabel) {
        Label nameLabel = new Label(name);
        nameLabel.setTextFill(Color.BLACK);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: 600;");
        HBox row = new HBox(12, nameLabel, valueLabel);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Label metricValueLabel() {
        Label label = new Label();
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: 700;");
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
    }

    private void resetSimulation() {
        model.reset(Math.toRadians(controlPanel.getSelectedAngle()));
        canvas.clearTrail();
        canvas.render();
        resetTelemetry();
        updateReadings();
        running = false;
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

        angleChart.plot(angleHistory, Color.web("#22c55e"));
        velocityChart.plot(velocityHistory, Color.web("#3b82f6"));
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
