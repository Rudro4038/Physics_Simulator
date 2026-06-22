package com.physicssim.components;

import com.physicssim.navigation.NavigationController;
import com.physicssim.navigation.ViewType;
import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class AppHeader extends HBox {

    public AppHeader(NavigationController navigationController) {
        Label brand = new Label("PHYSICS SIMULATOR - R13");
        brand.setFont(AppTheme.brandFont());
        brand.setTextFill(Color.web("#1b1f24"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox nav = new HBox(12);
        nav.setAlignment(Pos.CENTER_RIGHT);
        nav.getChildren().addAll(
                createNavButton("Home", ViewType.HOME, navigationController),
                createNavButton("Simulations", ViewType.SIMULATIONS, navigationController),
                createNavButton("About", ViewType.ABOUT, navigationController),
                createNavButton("Help", ViewType.HELP, navigationController));

        getChildren().addAll(brand, spacer, nav);
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(24);
        setPadding(new Insets(22, 34, 22, 34));
        setBackground(new Background(new BackgroundFill(AppTheme.SURFACE, CornerRadii.EMPTY, Insets.EMPTY)));
        setBorder(AppTheme.bottomBorder());
    }

    private Button createNavButton(String text, ViewType targetView, NavigationController navigationController) {
        Button button = new Button(text);
        button.setFocusTraversable(false);
        button.setCursor(Cursor.HAND);
        button.setBackground(Background.EMPTY);
        boolean active = navigationController.getCurrentView() == targetView;
        applyNavStyle(button, active);
        button.setBorder(Border.EMPTY);
        button.setPadding(new Insets(10, 14, 10, 14));
        button.setOnAction(event -> {
            navigationController.navigateTo(targetView);
            refreshNavigationState(navigationController);
        });
        return button;
    }

    private void refreshNavigationState(NavigationController navigationController) {
        getChildren().stream()
                .filter(node -> node instanceof HBox)
                .map(node -> (HBox) node)
                .findFirst()
                .ifPresent(navBox -> {
                    for (int index = 0; index < navBox.getChildren().size(); index++) {
                        if (navBox.getChildren().get(index) instanceof Button button) {
                            ViewType targetView = switch (button.getText()) {
                                case "Home" -> ViewType.HOME;
                                case "Simulations" -> ViewType.SIMULATIONS;
                                case "About" -> ViewType.ABOUT;
                                default -> ViewType.HELP;
                            };
                            applyNavStyle(button, navigationController.getCurrentView() == targetView);
                        }
                    }
                });
    }

    private void applyNavStyle(Button button, boolean active) {
        button.setFont(AppTheme.navFont(active));
        button.setTextFill(active ? Color.web("#101827") : Color.web("#596579"));
    }
}
