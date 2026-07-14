package com.physicssim.features.mechanics;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MomentumCalculatorView extends MechanicsToolLayout {

    private final Slider massSlider = new Slider(0.5, 20, 4);
    private final Slider velocitySlider = new Slider(0.5, 30, 6);
    private final Label massValue = valueLabel();
    private final Label velocityValue = valueLabel();
    private final Label momentumValue = resultLabel();

    public MomentumCalculatorView(Runnable onBack) {
        super("Momentum Calculator", "Use p = mv to calculate linear momentum from mass and velocity.", onBack);
        setToolContent(buildContent());
        updateResult();
    }

    private VBox buildContent() {
        massSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateResult());
        velocitySlider.valueProperty().addListener((obs, oldValue, newValue) -> updateResult());

        VBox content = new VBox(18,
                statBlock("Mass", massValue),
                massSlider,
                statBlock("Velocity", velocityValue),
                velocitySlider,
                statBlock("Momentum", momentumValue));
        content.setAlignment(Pos.TOP_LEFT);
        return content;
    }

    private void updateResult() {
        double mass = massSlider.getValue();
        double velocity = velocitySlider.getValue();
        double momentum = mass * velocity;

        massValue.setText(String.format("%.2f kg", mass));
        velocityValue.setText(String.format("%.2f m/s", velocity));
        momentumValue.setText(String.format("%.2f kg.m/s", momentum));
    }

    private VBox statBlock(String name, Label value) {
        Label label = label(name);
        return new VBox(6, label, value);
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

    private Label resultLabel() {
        Label label = new Label();
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: 900;");
        return label;
    }
}
