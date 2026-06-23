package com.physicssim.features.mechanics;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HookesLawSimulatorView extends MechanicsToolLayout {

    private final Slider springConstantSlider = new Slider(20, 300, 120);
    private final Slider extensionSlider = new Slider(0.01, 0.6, 0.15);
    private final Label forceValue = valueLabel();
    private final Label springValue = valueLabel();
    private final Label extensionValue = valueLabel();
    private final Pane scenePane = new Pane();
    private final Line spring = new Line();
    private final Rectangle block = new Rectangle(60, 40, Color.web("#f59e0b"));

    public HookesLawSimulatorView(Runnable onBack) {
        super("Hooke's Law Simulator", "Explore how force changes with extension using Hooke's Law: F = kx.", onBack);
        setToolContent(buildContent());
        updateSimulation();
    }

    private VBox buildContent() {
        scenePane.setPrefSize(430, 240);
        scenePane.setStyle("-fx-background-color: linear-gradient(to bottom, #fafafa, #eff6ff);"
                + "-fx-background-radius: 16;"
                + "-fx-border-color: #d9e2ee;"
                + "-fx-border-radius: 16;");
        spring.setStroke(Color.web("#0f172a"));
        spring.setStrokeWidth(4);
        scenePane.getChildren().addAll(spring, block);

        springConstantSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateSimulation());
        extensionSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateSimulation());

        VBox controls = new VBox(14,
                statBlock("Spring Constant", springValue),
                springConstantSlider,
                statBlock("Extension", extensionValue),
                extensionSlider,
                statBlock("Force", forceValue));
        controls.setAlignment(Pos.TOP_LEFT);

        return new VBox(new HBox(24, scenePane, controls));
    }

    private void updateSimulation() {
        double k = springConstantSlider.getValue();
        double x = extensionSlider.getValue();
        double force = k * x;

        springValue.setText(String.format("%.1f N/m", k));
        extensionValue.setText(String.format("%.2f m", x));
        forceValue.setText(String.format("%.2f N", force));

        double startX = 60;
        double endX = startX + 120 + x * 220;
        spring.setStartX(startX);
        spring.setStartY(120);
        spring.setEndX(endX);
        spring.setEndY(120);
        block.setX(endX);
        block.setY(100);
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
}
