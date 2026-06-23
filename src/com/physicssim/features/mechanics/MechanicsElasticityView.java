package com.physicssim.features.mechanics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class MechanicsElasticityView extends BorderPane {

    private final BorderPane contentHost = new BorderPane();

    public MechanicsElasticityView() {
        showHub();
        setCenter(contentHost);
    }

    private void showHub() {
        FlowPane cards = new FlowPane();
        cards.setHgap(20);
        cards.setVgap(20);
        cards.setAlignment(Pos.TOP_LEFT);

        for (MechanicsToolItem item : MechanicsCatalog.tools()) {
            cards.getChildren().add(new MechanicsToolCard(item, () -> openTool(item.getType())));
        }

        VBox page = new VBox(18, cards);
        page.setPadding(new Insets(12));
        contentHost.setCenter(buildPage(page));
    }

    private void openTool(MechanicsToolType type) {
        Node toolView = switch (type) {
            case FREE_FALL -> new FreeFallView(this::showHub);
            case PROJECTILE_MOTION -> new ProjectileMotionView(this::showHub);
            case MOMENTUM_CALCULATOR -> new MomentumCalculatorView(this::showHub);
            case HOOKES_LAW -> new HookesLawSimulatorView(this::showHub);
            case SPRING_EXTENSION -> new SpringExtensionCalculatorView(this::showHub);
        };
        contentHost.setCenter(buildPage(toolView));
    }

    private ScrollPane buildPage(Node content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        return scrollPane;
    }
}
