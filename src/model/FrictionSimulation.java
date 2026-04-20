package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class FrictionSimulation implements ISimulation {
    private final List<Ball> balls = new ArrayList<>();
    private final GravityEngine engine = new GravityEngine();
    boolean running = false;

    public void update() {
        engine.update(balls, 800, 600);
        for (Ball ball : balls) {
            ball.updateView();
        }
    }

    public void render() {
        for (Ball ball : balls) {
            ball.updateView();
        }
    }
}
