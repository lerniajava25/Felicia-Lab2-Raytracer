package org.raytracer.geometry;

import org.raytracer.math.Color;
import org.raytracer.math.Ray;

import java.util.Optional;

public abstract class Shape {
    protected Color color;

    protected Shape(Color color) {
        this.color = color;
    }

    public Color color() {
        return color;
    }

    public abstract Optional<Intersection> hit(Ray ray);
}
