package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.*;
import model.Ball;


public class Raf extends Application {
    @Override
    public void start(Stage stage) {
        Model model = new Model();
        View view = new View(800, 600);

        // 1. VIEW -> CONTROLLER: Capture UI event and update Model state
        view.canvas.setOnMouseClicked(e -> model.addBall(e.getX(), e.getY()));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // 2. CONTROLLER -> MODEL: Advance the physics math
                model.update(); 

                // 3 & 4. MODEL -> CONTROLLER -> VIEW: Fetch data and dispatch to renderer
                view.render(model.getEntities()); 
            }
        }.start();

        stage.setScene(new Scene(view.pane));
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}

// --- THE MODEL (Data & Logic) ---
class Model {
    private final List<Ball> balls = new ArrayList<>();

    public void addBall(double x, double y) { balls.add(new Ball(x, y)); }
    public List<Ball> getEntities() { return balls; }

    public void update() {
        // 5. MODEL -> ENTITY: Apply physics logic (Gravity from Gravity.java)
        balls.forEach(b -> { b.vy += 0.5; b.y += b.vy; if(b.y > 580) b.vy *= -0.9; });
    }
}



// --- THE VIEW (Pixels & Canvas) ---
class View {
    StackPane pane = new StackPane();
    Canvas canvas = new Canvas(800, 600);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    View(double w, double h) { pane.getChildren().add(canvas); }

    public void render(List<Ball> balls) {
        gc.clearRect(0, 0, 800, 600); // Clear frame
        gc.setFill(javafx.scene.paint.Color.TOMATO);
        for (Ball b : balls) {
            // 6. VIEW -> SYSTEM: Issue draw calls to the JavaFX graphics engine
            gc.fillOval(b.x, b.y, 20, 20); 
        }
    }
}