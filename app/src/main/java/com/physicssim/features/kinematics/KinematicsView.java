package com.physicssim.features.kinematics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class KinematicsView extends BorderPane {

    private final DistanceDisplacementView distanceDisplacementView = new DistanceDisplacementView();
    private final SpeedVelocityView speedVelocityView = new SpeedVelocityView();
    private final AccelerationView accelerationView = new AccelerationView();
    private final FreeFallView freeFallView = new FreeFallView();
    private final ProjectileMotionView projectileMotionView = new ProjectileMotionView();

    private final Pane contentHost = new Pane();

    public KinematicsView() {
        setPadding(new Insets(18));
        setStyle("-fx-background-color: transparent;");

        Label title = new Label("🏃  Kinematics (Motion)  🏃");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: linear-gradient(from 0% 0% to 100% 100%, #7dd3fc, #8b5cf6); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);");
        title.setAlignment(Pos.CENTER);

        Label description = new Label(
                "Explore fundamental concepts of motion through interactive simulations.");
        description.setStyle("-fx-font-size: 16px; -fx-text-fill: #64748b; -fx-wrap-text: true;");
        description.setPrefWidth(800);
        description.setAlignment(Pos.CENTER);

        Button backBtn = new Button("Back to Module List");
        backBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: 700; -fx-background-color: #e2e8f0; -fx-text-fill: #1e293b; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-padding: 8 16;");

        Button distanceBtn = new Button("Distance & Displacement");
        distanceBtn.setOnAction(event -> showSimulation(distanceDisplacementView));

        Button speedBtn = new Button("Speed & Velocity");
        speedBtn.setOnAction(event -> showSimulation(speedVelocityView));

        Button accelerationBtn = new Button("Acceleration");
        accelerationBtn.setOnAction(event -> showSimulation(accelerationView));

        Button freeFallBtn = new Button("Free Fall");
        freeFallBtn.setOnAction(event -> showSimulation(freeFallView));

        Button projectileBtn = new Button("Projectile Motion");
        projectileBtn.setOnAction(event -> showSimulation(projectileMotionView));

        HBox buttons = new HBox(12, backBtn, distanceBtn, speedBtn, accelerationBtn, freeFallBtn, projectileBtn);
        buttons.setAlignment(Pos.CENTER);

        VBox header = new VBox(12, title, description, buttons);
        header.setPadding(new Insets(16, 0, 24, 0));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #0f172a, #020617); -fx-background-radius: 12; -fx-padding: 20;");

        setTop(header);
        setCenter(contentHost);
        showSimulation(distanceDisplacementView);
    }

    private void showSimulation(Pane view) {
        contentHost.getChildren().clear();
        contentHost.getChildren().add(view);
    }
}
