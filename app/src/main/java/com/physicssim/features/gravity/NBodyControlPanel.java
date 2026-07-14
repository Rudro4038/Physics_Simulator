package com.physicssim.features.gravity;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Control panel for the N-Body simulation with comprehensive parameter adjustment.
 */
public class NBodyControlPanel extends VBox {

    // Bodies control
    private Spinner<Integer> bodyCountSpinner;
    private Button addBodiesButton;

    // Physics controls
    private Slider gravitySlider;
    private Label gravityValueLabel;

    // Simulation controls
    private Slider speedSlider;
    private Label speedValueLabel;

    // Circle arrangement controls
    private Slider radiusSlider;
    private Label radiusValueLabel;

    // Body appearance controls
    private Slider bodySizeSlider;
    private Label bodySizeValueLabel;

    // Visualization toggles
    private CheckBox showTrailsCheckBox;
    private CheckBox showVelocityCheckBox;

    // Action buttons
    private Button circularButton;
    private Button randomButton;
    private Button clearTrailsButton;
    private Button resetButton;

    public NBodyControlPanel() {
        setPadding(new Insets(15));
        setSpacing(15);
        setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white;");

        // Bodies Control Section
        getChildren().add(createSectionLabel("Bodies"));
        getChildren().add(createBodiesSection());

        // Physics Control Section
        getChildren().add(createSectionLabel("Physics"));
        getChildren().add(createPhysicsSection());

        // Simulation Speed Section
        getChildren().add(createSectionLabel("Simulation"));
        getChildren().add(createSpeedSection());

        // Circle Arrangement Section
        getChildren().add(createSectionLabel("Circle Arrangement"));
        getChildren().add(createCircleRadiusSection());

        // Body Appearance Section
        getChildren().add(createSectionLabel("Appearance"));
        getChildren().add(createAppearanceSection());

        // Visualization Options
        getChildren().add(createSectionLabel("Visualization"));
        getChildren().add(createVisualizationSection());

        // Action Buttons
        getChildren().add(createActionButtons());

        // Spacer
        getChildren().add(new Separator());
    }

    /**
     * Creates a styled section label.
     */
    private Label createSectionLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 12));
        label.setStyle("-fx-text-fill: #00ff88;");
        return label;
    }

    /**
     * Creates the bodies control section.
     */
    private VBox createBodiesSection() {
        VBox section = new VBox(8);

        // Body count spinner
        Label countLabel = new Label("Number of Bodies:");
        bodyCountSpinner = new Spinner<>(1, 100, 15, 1);
        bodyCountSpinner.setPrefWidth(80);
        bodyCountSpinner.setStyle("-fx-font-size: 11;");

        HBox countBox = new HBox(8);
        countBox.setAlignment(Pos.CENTER_LEFT);
        countBox.getChildren().addAll(countLabel, bodyCountSpinner);

        // Add bodies button
        addBodiesButton = new Button("Initialize");
        addBodiesButton.setPrefWidth(200);
        styleButton(addBodiesButton);

        section.getChildren().addAll(countBox, addBodiesButton);
        return section;
    }

    /**
     * Creates the physics control section.
     */
    private VBox createPhysicsSection() {
        VBox section = new VBox(8);

        Label gravityLabel = new Label("Gravity:");
        gravitySlider = new Slider(1, 500, 100);
        gravitySlider.setShowTickLabels(false);
        gravitySlider.setShowTickMarks(false);
        gravityValueLabel = new Label("100.0");
        gravityValueLabel.setStyle("-fx-text-fill: #00ff88;");

        HBox gravityBox = new HBox(8);
        gravityBox.setAlignment(Pos.CENTER_LEFT);
        gravityBox.getChildren().addAll(gravityLabel, gravitySlider, gravityValueLabel);
        HBox.setHgrow(gravitySlider, javafx.scene.layout.Priority.ALWAYS);

        section.getChildren().add(gravityBox);
        return section;
    }

    /**
     * Creates the simulation speed control section.
     */
    private VBox createSpeedSection() {
        VBox section = new VBox(8);

        Label speedLabel = new Label("Speed:");
        speedSlider = new Slider(0.1, 5.0, 1.0);
        speedSlider.setShowTickLabels(false);
        speedSlider.setShowTickMarks(false);
        speedValueLabel = new Label("1.0x");
        speedValueLabel.setStyle("-fx-text-fill: #00ff88;");

        HBox speedBox = new HBox(8);
        speedBox.setAlignment(Pos.CENTER_LEFT);
        speedBox.getChildren().addAll(speedLabel, speedSlider, speedValueLabel);
        HBox.setHgrow(speedSlider, javafx.scene.layout.Priority.ALWAYS);

        section.getChildren().add(speedBox);
        return section;
    }

    /**
     * Creates the circle arrangement control section.
     */
    private VBox createCircleRadiusSection() {
        VBox section = new VBox(8);

        Label radiusLabel = new Label("Circle Radius:");
        radiusSlider = new Slider(2, 300, 150);
        radiusSlider.setShowTickLabels(false);
        radiusSlider.setShowTickMarks(false);
        radiusValueLabel = new Label("150");
        radiusValueLabel.setStyle("-fx-text-fill: #00ff88;");

        HBox radiusBox = new HBox(8);
        radiusBox.setAlignment(Pos.CENTER_LEFT);
        radiusBox.getChildren().addAll(radiusLabel, radiusSlider, radiusValueLabel);
        HBox.setHgrow(radiusSlider, javafx.scene.layout.Priority.ALWAYS);

        section.getChildren().add(radiusBox);
        return section;
    }

    /**
     * Creates the body appearance control section.
     */
    private VBox createAppearanceSection() {
        VBox section = new VBox(8);

        Label sizeLabel = new Label("Body Size:");
        bodySizeSlider = new Slider(2.0, 20.0, 6.0);
        bodySizeSlider.setShowTickLabels(false);
        bodySizeSlider.setShowTickMarks(false);
        bodySizeValueLabel = new Label("6.0");
        bodySizeValueLabel.setStyle("-fx-text-fill: #00ff88;");

        HBox sizeBox = new HBox(8);
        sizeBox.setAlignment(Pos.CENTER_LEFT);
        sizeBox.getChildren().addAll(sizeLabel, bodySizeSlider, bodySizeValueLabel);
        HBox.setHgrow(bodySizeSlider, javafx.scene.layout.Priority.ALWAYS);

        section.getChildren().add(sizeBox);
        return section;
    }

    /**
     * Creates the visualization options section.
     */
    private VBox createVisualizationSection() {
        VBox section = new VBox(8);

        showTrailsCheckBox = new CheckBox("Show Trails");
        showTrailsCheckBox.setSelected(true);
        showTrailsCheckBox.setStyle("-fx-font-size: 11;");

        showVelocityCheckBox = new CheckBox("Show Velocity Vectors");
        showVelocityCheckBox.setSelected(true);
        showVelocityCheckBox.setStyle("-fx-font-size: 11;");

        section.getChildren().addAll(showTrailsCheckBox, showVelocityCheckBox);
        return section;
    }

    /**
     * Creates the action buttons section.
     */
    private VBox createActionButtons() {
        VBox section = new VBox(8);

        circularButton = new Button("Circular Arrangement");
        styleButton(circularButton);

        randomButton = new Button("Random Arrangement");
        styleButton(randomButton);

        clearTrailsButton = new Button("Clear Trails");
        styleButton(clearTrailsButton);

        resetButton = new Button("Reset");
        styleButton(resetButton);

        section.getChildren().addAll(
                circularButton,
                randomButton,
                clearTrailsButton,
                resetButton
        );
        return section;
    }

    /**
     * Applies consistent styling to buttons.
     */
    private void styleButton(Button button) {
        button.setPrefWidth(200);
        button.setStyle(
                "-fx-font-size: 11; " +
                "-fx-padding: 8; " +
                "-fx-background-color: #2a5f2a; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: #00ff88; " +
                "-fx-border-width: 1; " +
                "-fx-cursor: hand;"
        );
    }

    // ===== Getters =====

    public Spinner<Integer> getBodyCountSpinner() {
        return bodyCountSpinner;
    }

    public Button getAddBodiesButton() {
        return addBodiesButton;
    }

    public Slider getGravitySlider() {
        return gravitySlider;
    }

    public Label getGravityValueLabel() {
        return gravityValueLabel;
    }

    public Slider getSpeedSlider() {
        return speedSlider;
    }

    public Label getSpeedValueLabel() {
        return speedValueLabel;
    }

    public Slider getRadiusSlider() {
        return radiusSlider;
    }

    public Label getRadiusValueLabel() {
        return radiusValueLabel;
    }

    public Slider getBodySizeSlider() {
        return bodySizeSlider;
    }

    public Label getBodySizeValueLabel() {
        return bodySizeValueLabel;
    }

    public CheckBox getShowTrailsCheckBox() {
        return showTrailsCheckBox;
    }

    public CheckBox getShowVelocityCheckBox() {
        return showVelocityCheckBox;
    }

    public Button getCircularButton() {
        return circularButton;
    }

    public Button getRandomButton() {
        return randomButton;
    }

    public Button getClearTrailsButton() {
        return clearTrailsButton;
    }

    public Button getResetButton() {
        return resetButton;
    }
}