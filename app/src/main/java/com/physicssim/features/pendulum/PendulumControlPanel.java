package com.physicssim.features.pendulum;

import com.physicssim.components.PhysicsButton;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class PendulumControlPanel extends VBox {

    private final Button playPauseButton;
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
        Label subtitle = new Label("Fine-tune motion and watch the scene respond.");
        subtitle.setTextFill(Color.web("#1e293b"));
        subtitle.setStyle("-fx-font-size: 12px; -fx-font-weight: 600;");

        VBox gravityBlock = buildSliderBlock("Gravity", gravityValueLabel, gravitySlider, onGravityChanged, "%.2f m/s^2");
        VBox lengthBlock = buildSliderBlock("Rod Length", lengthValueLabel, lengthSlider, onLengthChanged, "%.2f m");
        VBox massBlock = buildSliderBlock("Bob Mass", massValueLabel, massSlider, onMassChanged, "%.2f kg");
        VBox angleBlock = buildSliderBlock("Initial Angle", angleValueLabel, angleSlider, onAngleChanged, "%.1f deg");

        playPauseButton = PhysicsButton.createStyled("", Color.web("#2d2d2d"), Color.web("#111111"));
        Button resetButton = PhysicsButton.createStyled("", Color.web("#2d2d2d"), Color.web("#111111"));
        playPauseButton.setGraphic(createPauseIcon());
        resetButton.setGraphic(createResetIcon());
        playPauseButton.setOnAction(event -> onPlayPause.run());
        resetButton.setOnAction(event -> onReset.run());
        playPauseButton.setPrefSize(44, 38);
        resetButton.setPrefSize(44, 38);
        playPauseButton.setTextFill(Color.WHITE);
        resetButton.setTextFill(Color.WHITE);
        playPauseButton.setStyle(playPauseButton.getStyle() + " -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: 700; -fx-background-radius: 14;");
        resetButton.setStyle(resetButton.getStyle() + " -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: 700; -fx-background-radius: 14;");

        HBox buttonRow = new HBox(10, playPauseButton, resetButton);
        buttonRow.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(new VBox(3, title, subtitle), gravityBlock, lengthBlock, massBlock, angleBlock, buttonRow);
        setSpacing(13);
        setAlignment(Pos.TOP_LEFT);
        setPadding(new Insets(16));
        setPrefWidth(250);
        setMinWidth(250);
        setBackground(new Background(new BackgroundFill(Color.web("#f8fbff"), new CornerRadii(20), Insets.EMPTY)));
        setBorder(new Border(new BorderStroke(
                Color.web("#dce7f3"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(20),
                new BorderWidths(1))));
        setStyle("-fx-effect: dropshadow(gaussian, rgba(15, 23, 32, 0.08), 18, 0.18, 0, 6);"
                + "-fx-background-radius: 20;"
                + "-fx-border-radius: 20;");
    }

    public double getSelectedAngle() {
        return angleSlider.getValue();
    }

    public void setPlayPauseButton(boolean running) {
        if (running) {
            playPauseButton.setGraphic(createPauseIcon());
            playPauseButton.setBackground(new Background(new BackgroundFill(Color.web("#2d2d2d"), new CornerRadii(14), Insets.EMPTY)));
        } else {
            playPauseButton.setGraphic(createPlayIcon());
            playPauseButton.setBackground(new Background(new BackgroundFill(Color.web("#111111"), new CornerRadii(14), Insets.EMPTY)));
        }
    }

    private StackPane createSvgIcon(String svgContent, boolean filled) {
        SVGPath path = new SVGPath();
        path.setContent(svgContent);
        if (filled) {
            path.setFill(Color.WHITE);
            path.setStroke(null);
        } else {
            path.setFill(null);
            path.setStroke(Color.WHITE);
            path.setStrokeWidth(2.5);
            path.setStrokeLineCap(StrokeLineCap.ROUND);
            path.setStrokeLineJoin(StrokeLineJoin.ROUND);
        }
        StackPane pane = new StackPane(path);
        pane.setPrefSize(20, 20);
        pane.setMinSize(20, 20);
        return pane;
    }

    private StackPane createPauseIcon() {
        return createSvgIcon("M 6 4 h 4 v 16 h -4 Z M 14 4 h 4 v 16 h -4 Z", true);
    }

    private StackPane createPlayIcon() {
        return createSvgIcon("M 6 4 L 20 12 L 6 20 Z", true);
    }

    private StackPane createResetIcon() {
        SVGPath path = new SVGPath();
        path.setContent("M 16 6 A 10 10 0 1 0 6 16");
        path.setStroke(Color.WHITE);
        path.setStrokeWidth(2.5);
        path.setFill(null);
        path.setStrokeLineCap(StrokeLineCap.ROUND);

        SVGPath arrow = new SVGPath();
        arrow.setContent("M 6 16 L 10 18 M 6 16 L 10 14");
        arrow.setStroke(Color.WHITE);
        arrow.setStrokeWidth(2.5);
        arrow.setFill(null);
        arrow.setStrokeLineCap(StrokeLineCap.ROUND);
        arrow.setStrokeLineJoin(StrokeLineJoin.ROUND);

        StackPane pane = new StackPane(path, arrow);
        pane.setPrefSize(20, 20);
        pane.setMinSize(20, 20);
        return pane;
    }

    private void addSliderGlow(Slider slider) {
        slider.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                Platform.runLater(() -> {
                    Node thumb = slider.lookup(".thumb");
                    if (thumb != null && thumb.getEffect() == null) {
                        thumb.setEffect(new DropShadow(10, Color.web("#2d2d2d", 0.45)));
                    }
                });
            }
        });
    }

    private VBox buildSliderBlock(
            String name,
            Label valueLabel,
            Slider slider,
            Consumer<Double> onChanged,
            String format) {
        Label nameLabel = sectionLabel(name);
        nameLabel.setMinHeight(18);

        valueLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 700; -fx-text-fill: #1d4ed8;");
        valueLabel.setMinWidth(92);
        valueLabel.setMinHeight(18);
        valueLabel.setAlignment(Pos.CENTER_LEFT);
        valueLabel.setVisible(true);
        valueLabel.setManaged(true);

        slider.setMajorTickUnit((slider.getMax() - slider.getMin()) / 4.0);
        slider.setMinorTickCount(2);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
        slider.setBlockIncrement((slider.getMax() - slider.getMin()) / 20.0);
        slider.setStyle("-fx-control-inner-background: white; -fx-accent: #2563eb;");
        addSliderGlow(slider);
        slider.valueProperty().addListener((obs, oldValue, newValue) -> {
            valueLabel.setText(String.format(format, newValue.doubleValue()));
            onChanged.accept(newValue.doubleValue());
        });
        valueLabel.setText(String.format(format, slider.getValue()));

        Label helperLabel = new Label("Adjust to update the simulation instantly");
        helperLabel.setTextFill(Color.web("#556270"));
        helperLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: 600;");
        helperLabel.setMinHeight(14);

        VBox block = new VBox(6, nameLabel, valueLabel, slider, helperLabel);
        block.setAlignment(Pos.TOP_LEFT);
        return block;
    }

    private Label sectionTitle(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.web("#0f172a"));
        label.setStyle("-fx-font-size: 17px; -fx-font-weight: 800; -fx-text-fill: #0f172a;");
        return label;
    }

    private Label sectionLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.web("#0f172a"));
        label.setStyle("-fx-font-size: 13px; -fx-font-weight: 800; -fx-text-fill: #0f172a;");
        return label;
    }

}
