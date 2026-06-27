package com.physicssim.features.gravity;

import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GravityView extends BorderPane {

    private final NBodyView nbodyView = new NBodyView();

    public GravityView() {
        setPadding(new Insets(12));
        setBackground(AppTheme.pageBackground());
        setTop(buildHeader());
        setCenter(nbodyView);
    }

    private Node buildHeader() {
        Label title = new Label("Current electricity");
        title.setFont(AppTheme.cardTitleFont());

        Button ohmsBtn = new Button("N Body Movement");

        ohmsBtn.setOnAction(e -> setCenter(nbodyView));

        HBox buttons = new HBox(8, ohmsBtn);
        buttons.setAlignment(Pos.CENTER_LEFT);

        VBox header = new VBox(8, title, buttons);
        header.setPadding(new Insets(20));
        header.setBackground(AppTheme.surfaceBackground());
        header.setBorder(AppTheme.cardBorder());
        return header;
    }
}
