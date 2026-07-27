package com.physicssim.features.pendulum;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PendulumView extends BorderPane {

    private final PendulumSimulationView simulationView = new PendulumSimulationView();
    private final Pane contentHost = new Pane();

    public PendulumView() {
        setPadding(new Insets(18));
        setStyle("-fx-background-color: transparent;");

        Label title = new Label("🕰️  Pendulum Dynamics  🕰️");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: linear-gradient(from 0% 0% to 100% 100%, #7dd3fc, #8b5cf6); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);");
        title.setAlignment(Pos.CENTER);

        Label description = new Label(
                "Explore simple harmonic motion with live controls, simulation, and motion charts.");
        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #64748b; -fx-wrap-text: true;");
        description.setPrefWidth(800);
        description.setAlignment(Pos.CENTER);

        Button backBtn = new Button("Back to Module List");
        backBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: 700; -fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-padding: 8 16;");

        Button simulationBtn = new Button("Pendulum Simulation");
        simulationBtn.setOnAction(event -> showSimulation(simulationView));

        HBox buttons = new HBox(12, backBtn, simulationBtn);
        buttons.setAlignment(Pos.CENTER);

        VBox header = new VBox(12, title, description, buttons);
        header.setPadding(new Insets(16, 0, 24, 0));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #0f172a, #020617); -fx-background-radius: 12; -fx-padding: 20;");

        setTop(header);
        setCenter(contentHost);
        showSimulation(simulationView);
    }

    private void showSimulation(Pane view) {
        contentHost.getChildren().clear();
        contentHost.getChildren().add(view);
    }
}
