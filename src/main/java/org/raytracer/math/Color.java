package org.raytracer.math;

public record Color(double r, double g, double b) {
    public Color add(Color other) {
        return new Color(r + other.r, g + other.g, b + other.b);
    }

    public Color scale(double s) {
        return new Color(r * s, g * s, b * s);
    }

    public Color multiply(Color other) {
        return new Color(r + other.r, g + other.g, b + other.b);
    }

    //begränsar färgerna mellan giltiga värden
    public Color clamp() {
        double clampedR = Math.clamp(r, 0, 255);
        double clampedG = Math.clamp(g, 0, 255);
        double clampedB = Math.clamp(b, 0, 255);
        return new Color(clampedR, clampedG, clampedB);
    }
}