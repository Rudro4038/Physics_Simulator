package com.physicssim.app;

import com.physicssim.components.AppFooter;
import com.physicssim.components.AppHeader;
import com.physicssim.navigation.NavigationController;
import com.physicssim.navigation.ViewType;
import com.physicssim.theme.AppTheme;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AppShell extends BorderPane {

    public AppShell() {
        NavigationController navigationController = new NavigationController();
        AppHeader header = new AppHeader(navigationController);
        AppFooter footer = new AppFooter();

        VBox page = new VBox();
        page.setBackground(AppTheme.pageBackground());
        VBox.setVgrow(navigationController.getContentHost(), Priority.ALWAYS);
        page.getChildren().addAll(header, navigationController.getContentHost(), footer);

        setBackground(AppTheme.pageBackground());
        setCenter(page);

        navigationController.navigateTo(ViewType.HOME);
    }
}
