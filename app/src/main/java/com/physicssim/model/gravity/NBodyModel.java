package com.physicssim.model.gravity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * Simulates gravitational interactions between bodies using N-Body algorithm.
 * Includes stable physics integration and support for body trails.
 */
public class NBodyModel {

    // Gravitational constant (scaled for simulation)
    private double gravitationalConstant = 100.0;

    // Small value to prevent division by zero and numerical instability
    private static final double EPSILON = 1e-4;

    private final List<Body> bodies;
    private double simulationSpeed = 1.0;

    public NBodyModel() {
        bodies = new ArrayList<>();
    }

    /**
     * Advances the simulation by dt seconds, scaled by simulation speed.
     */
    public void update(double dt) {
        // Scale time step by simulation speed for user control
        double scaledDt = dt * simulationSpeed;

        int n = bodies.size();

        // Calculate gravitational forces between every pair (O(n²) complexity)
        for (int i = 0; i < n; i++) {
            Body bodyA = bodies.get(i);

            for (int j = i + 1; j < n; j++) {
                Body bodyB = bodies.get(j);

                // Vector from A to B
                Vector2D delta = bodyB.getPosition().subtract(bodyA.getPosition());
                double distance = delta.magnitude() + EPSILON;
                Vector2D direction = delta.normalize();

                // Newton's law of universal gravitation: F = G * m1 * m2 / r²
                double forceMagnitude = gravitationalConstant 
                        * bodyA.getMass() 
                        * bodyB.getMass() 
                        / (distance * distance);

                Vector2D force = direction.multiply(forceMagnitude);

                // Apply Newton's Third Law (equal and opposite forces)
                bodyA.applyForce(force);
                bodyB.applyForce(force.multiply(-1));
            }
        }

        // Integrate all bodies using Verlet integration for stability
        for (Body body : bodies) {
            body.update(scaledDt);
        }
    }

    /**
     * Initializes bodies in a circular, evenly-spaced arrangement.
     */
    public void initializeCircularArrangement(int numBodies, double centerX, double centerY, 
                                             double radius, double orbitSpeed) {
        clear();

        for (int i = 0; i < numBodies; i++) {
            // Calculate angle for even spacing
            double angle = (2 * Math.PI * i) / numBodies;
            
            // Position on circle
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            
            // Tangential velocity (perpendicular to radius)
            double vx = -orbitSpeed * Math.sin(angle);
            double vy = orbitSpeed * Math.cos(angle);
            
            // Random mass between 15 and 50
            double mass = 15 + Math.random() * 35;
            
            // Create color based on angle (hue)
            Color color = Color.hsb(angle * (360 / (2 * Math.PI)), 0.85, 0.95);
            
            Body body = new Body(mass, new Vector2D(x, y), new Vector2D(vx, vy), color, 50);
            bodies.add(body);
        }
    }

    /**
     * Initializes bodies in a random arrangement.
     */
    public void initializeRandomArrangement(int numBodies, double width, double height) {
        clear();

        for (int i = 0; i < numBodies; i++) {
            double x = Math.random() * width;
            double y = Math.random() * height;
            double vx = (Math.random() - 0.5) * 40;
            double vy = (Math.random() - 0.5) * 40;
            double mass = 15 + Math.random() * 35;
            Color color = Color.hsb(Math.random() * 360, 0.8, 0.9);
            
            Body body = new Body(mass, new Vector2D(x, y), new Vector2D(vx, vy), color, 50);
            bodies.add(body);
        }
    }

    /**
     * Adds a body to the simulation.
     */
    public void addBody(Body body) {
        bodies.add(body);
    }

    /**
     * Removes a body.
     */
    public void removeBody(Body body) {
        bodies.remove(body);
    }

    /**
     * Clears all bodies from the simulation.
     */
    public void clear() {
        bodies.clear();
    }

    /**
     * Clears trails for all bodies.
     */
    public void clearTrails() {
        for (Body body : bodies) {
            body.clearTrail();
        }
    }

    /**
     * Returns an unmodifiable list of bodies.
     */
    public List<Body> getBodies() {
        return Collections.unmodifiableList(bodies);
    }

    /**
     * Returns the current number of bodies in the simulation.
     */
    public int getBodyCount() {
        return bodies.size();
    }

    /**
     * Sets the gravitational constant.
     */
    public void setGravitationalConstant(double g) {
        this.gravitationalConstant = Math.max(0.1, g);
    }

    /**
     * Returns the gravitational constant.
     */
    public double getGravitationalConstant() {
        return gravitationalConstant;
    }

    /**
     * Sets the simulation speed multiplier.
     */
    public void setSimulationSpeed(double speed) {
        this.simulationSpeed = Math.max(0.1, Math.min(5.0, speed));
    }

    /**
     * Returns the simulation speed multiplier.
     */
    public double getSimulationSpeed() {
        return simulationSpeed;
    }

    /**
     * Sets the trail length for all bodies.
     */
    public void setTrailLength(int length) {
        for (Body body : bodies) {
            body.setTrailLength(length);
        }
    }
}