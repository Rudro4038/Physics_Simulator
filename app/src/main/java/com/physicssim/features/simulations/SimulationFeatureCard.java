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
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        title.setFont(AppTheme.cardTitleFont());
        title.setTextFill(AppTheme.TEXT_PRIMARY);

        getChildren().addAll(number, icon, title);
        getStyleClass().add("simulation-card");
        setSpacing(14);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(22));
        setPrefSize(220, 270);
        setBackground(new Background(new BackgroundFill(AppTheme.CARD_SURFACE, new CornerRadii(18), Insets.EMPTY)));
        setBorder(AppTheme.cardBorder());
        setCursor(Cursor.HAND);

        setOnMouseClicked(event -> onOpen.run());
        setOnMouseEntered(event -> setTranslateY(-4));
        setOnMouseExited(event -> setTranslateY(0));
    }
}
