package org.raytracer.light;

import org.raytracer.math.Vector3D;
import org.raytracer.math.Color;

public class PointLight {
    private final Vector3D position;
    private final Color color;

    public PointLight(Vector3D position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Vector3D position() {
        return position;
    }

    public Color color() {
        return color;
    }
}
