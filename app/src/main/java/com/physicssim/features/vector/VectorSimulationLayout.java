package com.physicssim.features.vector;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/** Shared layout helpers so vector simulations match the kinematics / electricity module UI. */
public final class VectorSimulationLayout {

    private static final String CANVAS_FRAME_STYLE =
            "-fx-background-color: linear-gradient(135deg, #667eea 0%, #764ba2 100%);"
                    + "-fx-background-radius: 20; -fx-border-color: #5b21b6; -fx-border-radius: 20;"
                    + "-fx-effect: dropshadow(gaussian, rgba(102, 126, 234, 0.4), 20, 0.25, 0, 8);";

    private VectorSimulationLayout() {
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

    public static String toolHeadingStyle() {
        return "-fx-font-size: 18px; -fx-font-weight: 900; -fx-text-fill: #312e81;"
                + "-fx-background-color: linear-gradient(to right, #c7d2fe, #a5b4fc);"
                + "-fx-background-radius: 10; -fx-padding: 8 16; -fx-background-insets: 0 4 0 0;";
    }
}
