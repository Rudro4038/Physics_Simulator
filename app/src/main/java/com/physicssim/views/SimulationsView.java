package com.physicssim.views;

import com.physicssim.components.PhysicsButton;
import com.physicssim.features.atomic_nuclear.AtomicNuclearView;
import com.physicssim.features.electricity.CurrentElectricityView;
import com.physicssim.features.gravity.GravityView;
import com.physicssim.features.kinematics.KinematicsView;
import com.physicssim.features.mechanics.MechanicsElasticityView;
import com.physicssim.features.pendulum.PendulumSimulationView;
import com.physicssim.features.simulations.SimulationFeatureCard;
import com.physicssim.features.vector.VectorAdditionView;
import com.physicssim.model.SimulationCatalog;
import com.physicssim.model.SimulationItem;
import com.physicssim.theme.AppTheme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SimulationsView extends BorderPane {

    private final BorderPane contentHost = new BorderPane();
    private final ScrollPane rootScroll = new ScrollPane();
    private final VBox layout = new VBox(26);

    public SimulationsView() {
        setBackground(AppTheme.pageBackground());
        buildLayout();

        rootScroll.setContent(layout);
        rootScroll.setFitToWidth(true);
        rootScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rootScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        rootScroll.setStyle("""
            -fx-background-color: transparent;
            -fx-background: transparent;
            -fx-padding: 0;
            """);

        setCenter(rootScroll);

        showCatalog();
    }

    private void buildLayout() {
        contentHost.setBackground(
                new Background(new BackgroundFill(AppTheme.SURFACE, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox.setVgrow(contentHost, Priority.ALWAYS);

        layout.getChildren().setAll(contentHost);
        layout.setPadding(new Insets(24, 20, 24, 20));
        layout.setFillWidth(true);
        VBox.setVgrow(contentHost, Priority.ALWAYS);
    }

    /**
     * Responsive catalog that automatically wraps cards and keeps them centered.
     */
    private void showCatalog() {

        FlowPane cards = new FlowPane();
        cards.setAlignment(Pos.TOP_CENTER);
        cards.setHgap(24);
        cards.setVgap(24);
        cards.setPadding(new Insets(12));

        cards.prefWrapLengthProperty().bind(layout.widthProperty().subtract(56));

        for (SimulationItem item : SimulationCatalog.homeItems()) {
            cards.getChildren().add(
                    new SimulationFeatureCard(item, () -> openSimulation(item))
            );
        }

        contentHost.setCenter(cards);
    }

    private void openSimulation(SimulationItem item) {

        contentHost.setTop(null);

        switch (item.getType()) {

            case PENDULUM -> contentHost.setCenter(
                    buildSimulationPage(new PendulumSimulationView()));

            case WORK_POWER_ENERGY -> contentHost.setCenter(
                    buildSimulationPage(new MechanicsElasticityView()));

            case KINEMATICS -> contentHost.setCenter(
                    buildSimulationPage(new KinematicsView()));

            case ELECTRICITY -> contentHost.setCenter(
                    buildSimulationPage(new CurrentElectricityView()));

            case GRAVITY -> contentHost.setCenter(
                    buildSimulationPage(new GravityView()));

            case ATOMIC_NUCLEAR -> contentHost.setCenter(
                    buildSimulationPage(new AtomicNuclearView()));

            case VECTOR -> contentHost.setCenter(
                    buildSimulationPage(new VectorAdditionView()));

            default -> {
                showCatalog();

                Label placeholder = createPlaceholderLabel(item);

                BorderPane placeholderPane = new BorderPane(placeholder);
                placeholderPane.setPadding(new Insets(50));

                contentHost.setTop(createBackBar());
                contentHost.setCenter(placeholderPane);
            }
        }
    }

    private Label createPlaceholderLabel(SimulationItem item) {
        Label placeholder = new Label(item.getTitle().replace("\n", " ") + " module is coming next.");
        placeholder.setFont(AppTheme.subtitleFont());
        placeholder.setTextFill(AppTheme.TEXT_SECONDARY);
        placeholder.setWrapText(true);
        placeholder.setStyle("-fx-text-alignment: center; -fx-line-spacing: 4;");
        
        return placeholder;
    }

    private ScrollPane buildSimulationPage(Node content) {

        VBox pageContent = new VBox(18, createBackBar(), content);
        pageContent.setFillWidth(true);
        pageContent.setPadding(new Insets(0, 12, 12, 12));

        ScrollPane scrollPane = new ScrollPane(pageContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("""
                -fx-background-color: transparent;
                -fx-background: transparent;
                -fx-padding: 0;
                """);

        return scrollPane;
    }

    private HBox createBackBar() {

        PhysicsButton backButton =
                new PhysicsButton("Back to all simulations",
                        PhysicsButton.Style.TEXT_ONLY);

        backButton.setFont(AppTheme.cardNumberFont());
        backButton.setTextFill(AppTheme.SURFACE);
        backButton.setBackground(
                new Background(
                        new BackgroundFill(
                                javafx.scene.paint.Color.web("#3157d5"),
                                new CornerRadii(12),
                                Insets.EMPTY)));

        backButton.setPadding(new Insets(10, 16, 10, 16));
        backButton.setStyle("-fx-font-smoothing-type: lcd;");

        backButton.setOnAction(event -> {
            showCatalog();
        });

        HBox bar = new HBox(backButton);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(0, 0, 18, 0));

        return bar;
    }

}