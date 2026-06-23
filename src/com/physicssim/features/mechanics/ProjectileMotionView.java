package com.physicssim.features.mechanics;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;

public class ProjectileMotionView extends MechanicsToolLayout {

    private final Slider speedSlider = new Slider(5, 50, 20);
    private final Slider angleSlider = new Slider(10, 80, 45);
    private final Label rangeValue = valueLabel();
    private final Label heightValue = valueLabel();
    private final Label timeValue = valueLabel();
    private final Pane scenePane = new Pane();
    private final Polyline path = new Polyline();
    private final Circle projectile = new Circle(8, Color.web("#22c55e"));

    public ProjectileMotionView(Runnable onBack) {
        super("Projectile Motion", "Explore launch angle and speed, then inspect range, flight time, and peak height.", onBack);
        setToolContent(buildContent());
        updateSimulation();
    }

    private VBox buildContent() {
        scenePane.setPrefSize(460, 280);
        scenePane.setStyle("-fx-background-color: linear-gradient(to bottom, #eff6ff, #ecfccb);"
                + "-fx-background-radius: 16;"
                + "-fx-border-color: #d9e2ee;"
                + "-fx-border-radius: 16;");
        path.setStroke(Color.web("#16a34a"));
        path.setStrokeWidth(3);
        scenePane.getChildren().addAll(path, projectile);

        speedSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateSimulation());
        angleSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateSimulation());

        VBox controls = new VBox(14,
                statBlock("Launch Speed", valueLabel(speedSlider, "%.1f m/s")),
                speedSlider,
                statBlock("Launch Angle", valueLabel(angleSlider, "%.1f deg")),
                angleSlider,
                statBlock("Range", rangeValue),
                statBlock("Max Height", heightValue),
                statBlock("Flight Time", timeValue));
        controls.setAlignment(Pos.TOP_LEFT);

        return new VBox(new HBox(24, scenePane, controls));
    }

    private void updateSimulation() {
        double speed = speedSlider.getValue();
        double angleDegrees = angleSlider.getValue();
        double angleRadians = Math.toRadians(angleDegrees);
        double gravity = 9.81;

        double flightTime = (2 * speed * Math.sin(angleRadians)) / gravity;
        double range = speed * Math.cos(angleRadians) * flightTime;
        double maxHeight = (speed * speed * Math.sin(angleRadians) * Math.sin(angleRadians)) / (2 * gravity);

        rangeValue.setText(String.format("%.2f m", range));
        heightValue.setText(String.format("%.2f m", maxHeight));
        timeValue.setText(String.format("%.2f s", flightTime));

        path.getPoints().clear();
        for (int step = 0; step <= 60; step++) {
            double t = flightTime * step / 60.0;
            double x = speed * Math.cos(angleRadians) * t;
            double y = (speed * Math.sin(angleRadians) * t) - (0.5 * gravity * t * t);
            path.getPoints().addAll(30 + x * 3.2, 240 - y * 7.0);
        }

        if (path.getPoints().size() >= 2) {
            int size = path.getPoints().size();
            projectile.setCenterX(path.getPoints().get(size - 2));
            projectile.setCenterY(path.getPoints().get(size - 1));
        }
    }

    private VBox statBlock(String name, Label value) {
        Label label = label(name);
        return new VBox(6, label, value);
    }

    private Label valueLabel(Slider slider, String pattern) {
        Label label = valueLabel();
        label.setText(String.format(pattern, slider.getValue()));
        slider.valueProperty().addListener((obs, oldValue, newValue) -> label.setText(String.format(pattern, newValue.doubleValue())));
        return label;
    }

    private Label label(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 15px; -fx-font-weight: 700;");
        return label;
    }

    private Label valueLabel() {
        Label label = new Label();
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: 800;");
        return label;
    }
}
