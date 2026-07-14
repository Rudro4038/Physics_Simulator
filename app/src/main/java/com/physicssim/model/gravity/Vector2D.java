package com.physicssim.model.gravity;

/**
 * Represents a 2D vector with common mathematical operations.
 * Used for position, velocity, and force calculations.
 */
public class Vector2D {

    private final double x;
    private final double y;

    /**
     * Creates a vector with specified x and y components.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the magnitude (length) of this vector.
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns a normalized vector (unit vector) in the same direction.
     */
    public Vector2D normalize() {
        double mag = magnitude();
        if (mag == 0) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(x / mag, y / mag);
    }

    /**
     * Returns the dot product with another vector.
     */
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Returns the sum of this vector and another.
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Returns the difference of this vector and another.
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Returns this vector scaled by a scalar.
     */
    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Returns this vector divided by a scalar.
     */
    public Vector2D divide(double scalar) {
        if (scalar == 0) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(this.x / scalar, this.y / scalar);
    }

    /**
     * Returns the distance to another vector.
     */
    public double distanceTo(Vector2D other) {
        return this.subtract(other).magnitude();
    }

    /**
     * Returns a copy of this vector.
     */
    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }

    // ===== Getters =====

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("Vector2D(%.2f, %.2f)", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2D)) {
            return false;
        }
        Vector2D other = (Vector2D) obj;
        return Double.compare(this.x, other.x) == 0 &&
                Double.compare(this.y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) ^ Double.hashCode(y);
    }
}