package com.physicssim.features.mechanics;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SpringExtensionCalculatorView extends MechanicsToolLayout {

    private final Slider forceSlider = new Slider(1, 120, 20);
    private final Slider springConstantSlider = new Slider(10, 300, 80);
    private final Label forceValue = valueLabel();
    private final Label springConstantValue = valueLabel();
    private final Label extensionValue = resultLabel();

    public SpringExtensionCalculatorView(Runnable onBack) {
        super("Spring Extension Calculator", "Calculate extension from x = F / k for a loaded spring.", onBack);
        setToolContent(buildContent());
        updateResult();
    }

    private VBox buildContent() {
        forceSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateResult());
        springConstantSlider.valueProperty().addListener((obs, oldValue, newValue) -> updateResult());

        VBox content = new VBox(18,
                statBlock("Applied Force", forceValue),
                forceSlider,
                statBlock("Spring Constant", springConstantValue),
                springConstantSlider,
                statBlock("Extension", extensionValue));
        content.setAlignment(Pos.TOP_LEFT);
        return content;
    }

    private void updateResult() {
        double force = forceSlider.getValue();
        double springConstant = springConstantSlider.getValue();
        double extension = force / springConstant;

        forceValue.setText(String.format("%.2f N", force));
        springConstantValue.setText(String.format("%.2f N/m", springConstant));
        extensionValue.setText(String.format("%.4f m", extension));
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
