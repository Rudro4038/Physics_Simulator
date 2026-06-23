package com.physicssim.features.pendulum;

import com.physicssim.components.PhysicsButton;
import com.physicssim.theme.AppTheme;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PendulumControlPanel extends VBox {

    private final Label gravityValueLabel = new Label();
    private final Label lengthValueLabel = new Label();
    private final Label massValueLabel = new Label();
    private final Label angleValueLabel = new Label();

    private final Slider gravitySlider = new Slider(1.0, 20.0, 9.81);
    private final Slider lengthSlider = new Slider(0.8, 3.0, 2.0);
    private final Slider massSlider = new Slider(0.5, 5.0, 1.5);
    private final Slider angleSlider = new Slider(5, 75, 30);

    public PendulumControlPanel(
            Runnable onPlayPause,
            Runnable onReset,
            Consumer<Double> onAngleChanged,
            Consumer<Double> onGravityChanged,
            Consumer<Double> onLengthChanged,
            Consumer<Double> onMassChanged) {
        Label title = sectionTitle("Pendulum Controls");

        VBox gravityBlock = buildSliderBlock("Gravity", gravityValueLabel, gravitySlider, onGravityChanged, "%.2f m/s^2");
        VBox lengthBlock = buildSliderBlock("Rod Length", lengthValueLabel, lengthSlider, onLengthChanged, "%.2f m");
        VBox massBlock = buildSliderBlock("Bob Mass", massValueLabel, massSlider, onMassChanged, "%.2f kg");
        VBox angleBlock = buildSliderBlock("Initial Angle", angleValueLabel, angleSlider, onAngleChanged, "%.1f deg");

        Button playPauseButton = PhysicsButton.createStyled("START / PAUSE", Color.web("#2fb15a"), Color.web("#1b7f3c"));
        Button resetButton = PhysicsButton.createStyled("RESET", Color.web("#e38a2e"), Color.web("#b9661f"));
        playPauseButton.setOnAction(event -> onPlayPause.run());
        resetButton.setOnAction(event -> onReset.run());

        getChildren().addAll(title, gravityBlock, lengthBlock, massBlock, angleBlock, playPauseButton, resetButton);
        setSpacing(18);
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(22));
        setPrefWidth(280);
        setMinWidth(280);
        setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), new CornerRadii(18), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(
                Color.web("#d9e2ee"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(18),
                new BorderWidths(1))));
        setStyle("-fx-effect: dropshadow(gaussian, rgba(15, 23, 32, 0.08), 18, 0.18, 0, 6);");
    }

    public double getSelectedAngle() {
        return angleSlider.getValue();
    }

    private VBox buildSliderBlock(
            String name,
            Label valueLabel,
            Slider slider,
            Consumer<Double> onChanged,
            String format) {
        Label nameLabel = sectionLabel(name);
        valueLabel.setFont(AppTheme.cardTitleFont());
        valueLabel.setTextFill(Color.BLACK);

        slider.setMajorTickUnit((slider.getMax() - slider.getMin()) / 4.0);
        slider.setMinorTickCount(3);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
        slider.valueProperty().addListener((obs, oldValue, newValue) -> {
            valueLabel.setText(String.format(format, newValue.doubleValue()));
            onChanged.accept(newValue.doubleValue());
        });
        valueLabel.setText(String.format(format, slider.getValue()));

        VBox block = new VBox(8, nameLabel, valueLabel, slider);
        block.setAlignment(Pos.TOP_LEFT);
        return block;
    }

    private Label sectionTitle(String text) {
        Label label = new Label(text);
        label.setFont(AppTheme.cardTitleFont());
        label.setTextFill(Color.BLACK);
        return label;
    }

    private Label sectionLabel(String text) {
        Label label = new Label(text);
        label.setFont(AppTheme.cardNumberFont());
        label.setTextFill(Color.BLACK);
        return label;
    }

}
