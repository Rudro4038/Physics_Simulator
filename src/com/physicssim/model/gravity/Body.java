package com.physicssim.model.gravity;

import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a body in the N-Body simulation.
 * Includes position, velocity, mass, color, and trail history.
 */
public class Body {

    private double mass;
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Color color;
    
    // Trail tracking
    private final Queue<Vector2D> trail;
    private final int maxTrailLength;

    /**
     * Creates a body with default color and trail length.
     */
    public Body(double mass, Vector2D position, Vector2D velocity) {
        this(mass, position, velocity, Color.hsb(Math.random() * 360, 0.8, 0.9), 50);
    }

    /**
     * Creates a body with specified color and trail length.
     */
    public Body(double mass, Vector2D position, Vector2D velocity, Color color, int trailLength) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = new Vector2D(0, 0);
        this.color = color;
        this.maxTrailLength = Math.max(5, trailLength);
        this.trail = new LinkedList<>();
    }

    /**
     * Applies a force to the body (F = ma).
     */
    public void applyForce(Vector2D force) {
        Vector2D accel = force.divide(mass);
        acceleration = acceleration.add(accel);
    }

    /**
     * Updates the body's position and velocity using Verlet integration.
     * Also maintains the trail history.
     */
    public void update(double dt) {
        // Record trail position before update
        trail.offer(new Vector2D(position.getX(), position.getY()));
        if (trail.size() > maxTrailLength) {
            trail.poll();
        }

        // Verlet integration for stability
        velocity = velocity.add(acceleration.multiply(dt));
        position = position.add(velocity.multiply(dt));

        // Reset acceleration (forces are recalculated each frame)
        acceleration = new Vector2D(0, 0);
    }

    /**
     * Clears the trail history.
     */
    public void clearTrail() {
        trail.clear();
    }

    /**
     * Sets the maximum trail length.
     */
    public void setTrailLength(int length) {
        while (trail.size() > length) {
            trail.poll();
        }
    }

    // ===== Getters & Setters =====

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = Math.max(1, mass);
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Queue<Vector2D> getTrail() {
        return new LinkedList<>(trail);
    }

    public int getTrailLength() {
        return trail.size();
    }
}