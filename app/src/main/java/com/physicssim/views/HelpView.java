package com.physicssim.views;

import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HelpView extends BorderPane {

    public HelpView() {
        setBackground(AppTheme.pageBackground());

        Label title = new Label("Help Center");
        title.setFont(AppTheme.heroFont());
        title.setTextFill(AppTheme.TEXT_PRIMARY);

        Label body = new Label(
                "Use the Simulations tab to open physics modules.\n"
                        + "We can add instructions, formulas, and controls for each simulation here later.");
        body.setFont(AppTheme.subtitleFont());
        body.setTextFill(AppTheme.TEXT_SECONDARY);
        body.setWrapText(true);
        body.setMaxWidth(780);

        VBox content = new VBox(24, title, body);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(70, 40, 70, 40));
        content.setBackground(AppTheme.surfaceBackground());

        BorderPane wrapper = new BorderPane(content);
        wrapper.setPadding(new Insets(28, 26, 28, 26));
        setCenter(wrapper);
    }
}
