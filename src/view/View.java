package view;

import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class View {
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