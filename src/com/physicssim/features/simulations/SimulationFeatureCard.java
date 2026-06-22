package com.physicssim.features.simulations;

import com.physicssim.components.SimulationIconFactory;
import com.physicssim.model.SimulationItem;
import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SimulationFeatureCard extends VBox {

    public SimulationFeatureCard(SimulationItem item, Runnable onOpen) {
        Label number = new Label(item.getNumber());
        number.setFont(AppTheme.cardNumberFont());
        number.setTextFill(AppTheme.TEXT_SECONDARY);

        StackPane icon = SimulationIconFactory.create(item.getType());

        Label title = new Label(item.getTitle());
        title.setFont(AppTheme.cardTitleFont());
        title.setTextFill(AppTheme.TEXT_PRIMARY);
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);

        Label action = new Label("Open module");
        action.setFont(AppTheme.cardNumberFont());
        action.setTextFill(javafx.scene.paint.Color.web("#3157d5"));

        getChildren().addAll(number, icon, title, action);
        setSpacing(14);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(22));
        setPrefSize(220, 270);
        setBackground(new Background(new BackgroundFill(AppTheme.SURFACE, new CornerRadii(18), Insets.EMPTY)));
        setBorder(AppTheme.cardBorder());
        setStyle("-fx-effect: dropshadow(gaussian, rgba(24, 39, 75, 0.12), 18, 0.25, 0, 6);");
        setCursor(Cursor.HAND);

        setOnMouseClicked(event -> onOpen.run());
        setOnMouseEntered(event -> {
            setTranslateY(-4);
            setStyle("-fx-effect: dropshadow(gaussian, rgba(24, 39, 75, 0.18), 24, 0.30, 0, 10);");
        });
        setOnMouseExited(event -> {
            setTranslateY(0);
            setStyle("-fx-effect: dropshadow(gaussian, rgba(24, 39, 75, 0.12), 18, 0.25, 0, 6);");
        });
    }
}
