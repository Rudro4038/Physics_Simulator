package model;

import java.util.List;

public class GravityEngine {
    private static final double GRAVITY = 0.5;
    private static final double FRICTION = 0.98;

    public void update(List<Ball> balls, double width, double height) {
        for (Ball ball : balls) {
            // 1. Apply Physics
            ball.vy += GRAVITY;
            ball.x += ball.vx;
            ball.y += ball.vy;

            // 2. Boundary Constraints
            if (ball.y + ball.radius > height) {
                ball.y = height - ball.radius;
                ball.vy *= -FRICTION;
            }
            if (ball.x + ball.radius > width || ball.x - ball.radius < 0) {
                ball.vx *= -FRICTION;
                ball.x = (ball.x < ball.radius) ? ball.radius : width - ball.radius;
            }
        }
    }
}