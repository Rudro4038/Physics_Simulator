import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PhysicsSim extends Application {

    // Physics Constants
    private static final double GRAVITY = 0.5;
    private static final double FRICTION = 0.98; // Energy lost on bounce
    private static double WIDTH = 800;
    private static double HEIGHT = 600;

    // Ball Properties
    private double x = 100, y = 100; // Position
    private double vx = 5, vy = 0; // Velocity
    private double radius = 20;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        // Set the background color to black
        root.setStyle("-fx-background-color: black;");

        // Change the circle color to blue
        Circle ball = new Circle(radius, Color.RED);
        root.getChildren().add(ball);

        // Bind the scene's width and height to the simulation's width and height
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            WIDTH = newVal.doubleValue();
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            HEIGHT = newVal.doubleValue();
        });

        // AnimationTimer runs at ~60 frames per second
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                updatePhysics();
                render(ball);
            }
        }.start();

        stage.setTitle("Java Physics Simulator");
        stage.setScene(scene);
        stage.show();
    }

    private void updatePhysics() {
        // 1. Apply Gravity
        vy += GRAVITY;

        // 2. Update Position
        x += vx;
        y += vy;

        // 3. Collision Detection (Floor)
        if (y + radius > HEIGHT) {
            y = HEIGHT - radius; // Snap back to surface
            vy *= -FRICTION; // Reverse and dampen velocity
        }

        // 4. Collision Detection (Walls)
        if (x + radius > WIDTH || x - radius < 0) {
            vx *= -FRICTION;
            x = (x < radius) ? radius : WIDTH - radius;
        }
    }

    private void render(Circle ball) {
        ball.setCenterX(x);
        ball.setCenterY(y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
