package com.physicssim.features.gravity;

import java.util.List;
import java.util.Queue;

import com.physicssim.model.gravity.Body;
import com.physicssim.model.gravity.Vector2D;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Renders the N-Body simulation with smooth trails and velocity vectors.
 */
public class NBodyCanvas extends Canvas {

    private boolean showVelocityVectors = true;
    private boolean showTrails = true;
    private double bodySize = 6.0;

    public NBodyCanvas(double width, double height) {
        super(width, height);
    }

    /**
     * Renders all bodies and their trails with smooth fading effect.
     */
    public void render(List<Body> bodies) {
        GraphicsContext gc = getGraphicsContext2D();

        // Clear canvas with black background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());

        // Render trails first (so they appear behind bodies)
        if (showTrails) {
            for (Body body : bodies) {
                renderTrail(gc, body);
            }
        }

        // Render bodies
        for (Body body : bodies) {
            renderBody(gc, body);
        }

        // Render velocity vectors if enabled
        if (showVelocityVectors) {
            for (Body body : bodies) {
                renderVelocityVector(gc, body);
            }
        }
    }

    /**
     * Renders a single body as a circle with its color.
     */
    private void renderBody(GraphicsContext gc, Body body) {
        double x = body.getPosition().getX();
        double y = body.getPosition().getY();
        double size = bodySize;

        gc.setFill(body.getColor());
        gc.fillOval(x - size / 2, y - size / 2, size, size);

        // Optional: Add a subtle glow/border
        gc.setStroke(body.getColor().brighter());
        gc.setLineWidth(1.0);
        gc.strokeOval(x - size / 2, y - size / 2, size, size);
    }

    /**
     * Renders the trail of a body with smooth fade effect.
     */
    private void renderTrail(GraphicsContext gc, Body body) {
        Queue<Vector2D> trail = body.getTrail();

        if (trail.size() < 2) {
            return;
        }

        Vector2D[] trailPoints = trail.toArray(new Vector2D[0]);
        int trailSize = trailPoints.length;

        gc.setLineWidth(2.0);

        // Draw trail segments with fading opacity
        for (int i = 0; i < trailSize - 1; i++) {
            // Calculate alpha: fade from transparent at start to opaque at end
            double alpha = (double) i / trailSize;
            
            Vector2D p1 = trailPoints[i];
            Vector2D p2 = trailPoints[i + 1];

            Color baseColor = body.getColor();
            Color fadeColor = new Color(
                    baseColor.getRed(),
                    baseColor.getGreen(),
                    baseColor.getBlue(),
                    alpha * 0.7  // Max opacity of 70%
            );

            gc.setStroke(fadeColor);
            gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
    }

    /**
     * Renders the velocity vector of a body.
     */
    private void renderVelocityVector(GraphicsContext gc, Body body) {
        Vector2D pos = body.getPosition();
        Vector2D vel = body.getVelocity();

        double x = pos.getX();
        double y = pos.getY();
        
        // Scale velocity for visibility
        double scale = 3.0;
        double endX = x + vel.getX() * scale;
        double endY = y + vel.getY() * scale;

        // Use a semi-transparent lime color
        gc.setStroke(new Color(0, 1, 0, 0.4));
        gc.setLineWidth(1.5);
        gc.strokeLine(x, y, endX, endY);
    }

    // ===== Getters & Setters =====

    public void setShowTrails(boolean show) {
        this.showTrails = show;
    }

    public boolean isShowTrails() {
        return showTrails;
    }

    public void setShowVelocityVectors(boolean show) {
        this.showVelocityVectors = show;
    }

    public boolean isShowVelocityVectors() {
        return showVelocityVectors;
    }

    public void setBodySize(double size) {
        this.bodySize = Math.max(2.0, Math.min(20.0, size));
    }

    public double getBodySize() {
        return bodySize;
    }
}