package com.physicssim.theme;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class AppTheme {

    /* Soft slate palette — lifted mid-tones for eye comfort & readability */
    public static final Color PAGE_BACKGROUND = Color.web("#2e3446");
    public static final Color SURFACE = Color.web("#363d52");
    public static final Color CARD_SURFACE = Color.web("#404968");
    public static final Color BORDER = Color.web("#4f5872");
    public static final Color CARD_BORDER = Color.web("#5c6680");
    public static final Color TEXT_PRIMARY = Color.web("#f5f0e8");
    public static final Color TEXT_SECONDARY = Color.web("#c8bfb2");
    public static final Color TEXT_MUTED = Color.web("#9a9390");
    public static final Color NAV_BACKGROUND = Color.web("#4a5470");
    public static final Color NAV_BACKGROUND_ACTIVE = Color.web("#556380");
    public static final Color NAV_TEXT = Color.web("#ddd5c8");
    public static final Color NAV_TEXT_ACTIVE = Color.web("#faf6f0");
    public static final Color ACCENT_BLUE = Color.web("#7ec8f2");
    public static final Color ICON_PRIMARY = Color.web("#e8dcc8");
    public static final Color ICON_MID = Color.web("#d4c8b4");
    public static final Color ICON_DARK = Color.web("#f0e8dc");
    public static final Color ICON_LIGHT = Color.web("#a09890");
    public static final Color ICON_ACCENT = Color.web("#7ec8f2");

    private AppTheme() {
    }

    public static Background surfaceBackground() {
        return new Background(new BackgroundFill(SURFACE, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Background pageBackground() {
        return new Background(new BackgroundFill(PAGE_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Background cardBackground() {
        return new Background(new BackgroundFill(CARD_SURFACE, new CornerRadii(18), Insets.EMPTY));
    }

    public static Border bottomBorder() {
        return new Border(new BorderStroke(
                BORDER,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(0, 0, 1, 0)));
    }

    public static Border topBorder() {
        return new Border(new BorderStroke(
                BORDER,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(1, 0, 0, 0)));
    }

    public static Border cardBorder() {
        return new Border(new BorderStroke(
                CARD_BORDER,
                BorderStrokeStyle.SOLID,
                new CornerRadii(18),
                new BorderWidths(1.2)));
    }

    public static Font brandFont() {
        return Font.font("Arial", FontWeight.EXTRA_BOLD, 28);
    }

    public static Font navFont(boolean active) {
        return Font.font("Arial", active ? FontWeight.BOLD : FontWeight.MEDIUM, 15);
    }

    public static Font heroFont() {
        return Font.font("Arial", FontWeight.EXTRA_BOLD, 48);
    }

    public static Font subtitleFont() {
        return Font.font("Arial", FontWeight.MEDIUM, 24);
    }

    public static Font cardNumberFont() {
        return Font.font("Arial", FontWeight.MEDIUM, 16);
    }

    public static Font cardTitleFont() {
        return Font.font("Arial", FontWeight.BOLD, 18);
    }

    public static Font footerFont() {
        return Font.font("Arial", FontWeight.NORMAL, 14);
    }
}
