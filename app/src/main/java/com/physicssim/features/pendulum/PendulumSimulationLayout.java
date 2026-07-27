package com.physicssim.features.pendulum;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/** Matches kinematics-style gradient frame around the pendulum scene. */
public final class PendulumSimulationLayout {

    private static final String CANVAS_FRAME_STYLE =
            "-fx-background-color: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);"
                    + "-fx-background-radius: 20; -fx-border-color: #0284c7; -fx-border-radius: 20;"
                    + "-fx-effect: dropshadow(gaussian, rgba(79, 172, 254, 0.4), 20, 0.25, 0, 8);";

    private PendulumSimulationLayout() {
    }

    public static Pane wrapSimulation(PendulumCanvas canvas) {
        Pane container = new Pane(canvas);
        container.setStyle(CANVAS_FRAME_STYLE);
        return container;
    }

    public static void setMainRow(BorderPane view, PendulumControlPanel controls, Pane simulationFrame, VBox metrics) {
        controls.setPrefWidth(300);
        metrics.setPrefWidth(280);
        metrics.setPadding(new Insets(8));
        metrics.setAlignment(Pos.TOP_LEFT);

        HBox topRow = new HBox(24, controls, simulationFrame, metrics);
        topRow.setAlignment(Pos.TOP_LEFT);
        view.setCenter(topRow);
    }
}
