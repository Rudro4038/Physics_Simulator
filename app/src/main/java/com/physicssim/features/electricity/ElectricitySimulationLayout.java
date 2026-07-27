package com.physicssim.features.electricity;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/** Shared layout helpers so electricity simulations match the kinematics module UI. */
public final class ElectricitySimulationLayout {

    private static final String CANVAS_FRAME_STYLE =
            "-fx-background-color: linear-gradient(135deg, #fbbf24 0%, #f59e0b 100%);"
                    + "-fx-background-radius: 20; -fx-border-color: #d97706; -fx-border-radius: 20;"
                    + "-fx-effect: dropshadow(gaussian, rgba(245, 158, 11, 0.4), 20, 0.25, 0, 8);";

    private ElectricitySimulationLayout() {
    }

    public static Pane wrapCanvas(Canvas canvas) {
        Pane container = new Pane(canvas);
        container.setStyle(CANVAS_FRAME_STYLE);
        return container;
    }

    public static void setCanvasAndControls(BorderPane view, Canvas canvas, VBox controls) {
        controls.setAlignment(Pos.TOP_LEFT);
        controls.setPadding(new Insets(8));
        controls.setPrefWidth(320);

        HBox body = new HBox(24, wrapCanvas(canvas), controls);
        body.setAlignment(Pos.TOP_LEFT);
        view.setCenter(body);
    }
}
