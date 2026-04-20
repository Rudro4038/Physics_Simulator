package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
    // State (Data)
    public double x, y, vx, vy, radius;
    private final Circle view; // The JavaFX representation

    public Ball(double x, double y) { 
        this.x = x; 
        this.y = y; 
        this.view = new Circle();
    }


    public Ball(double x, double y, double vx, double vy, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.view = new Circle(radius, color);
    }

    public Circle getView() {
        return view;
    }

    // Update the visual position based on logic state
    public void updateView() {
        view.setCenterX(x);
        view.setCenterY(y);
    }
}

// class Ball {
//     double x, y, vy = 0;
//     Ball(double x, double y) { this.x = x; this.y = y; }
// }