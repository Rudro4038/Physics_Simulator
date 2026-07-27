package com.physicssim.components;

import com.physicssim.model.SimulationItem;
import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class SimulationCard extends VBox {

    public SimulationCard(SimulationItem item) {
        Label numberLabel = new Label(item.getNumber());
        numberLabel.setFont(AppTheme.cardNumberFont());
        numberLabel.setTextFill(AppTheme.TEXT_SECONDARY);

        StackPane icon = SimulationIconFactory.create(item.getType());

        Label titleLabel = new Label(item.getTitle());
        titleLabel.setFont(AppTheme.cardTitleFont());
        titleLabel.setTextFill(AppTheme.TEXT_PRIMARY);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setWrapText(true);

        getChildren().addAll(numberLabel, icon, titleLabel);
        getStyleClass().add("simulation-card");
        setAlignment(Pos.TOP_CENTER);
        setSpacing(16);
        setPadding(new Insets(18, 16, 18, 16));
        setPrefSize(210, 250);
        setMaxSize(210, 250);
        setBackground(new Background(new BackgroundFill(AppTheme.CARD_SURFACE, new CornerRadii(18), Insets.EMPTY)));
        setBorder(AppTheme.cardBorder());
        setCursor(Cursor.HAND);

        setOnMouseEntered(event -> setTranslateY(-5));
        setOnMouseExited(event -> setTranslateY(0));
    }
}
