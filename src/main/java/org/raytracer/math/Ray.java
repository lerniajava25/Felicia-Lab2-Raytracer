package org.raytracer.math;

public record Ray(Vector3D origin, Vector3D direction) {

    public Vector3D pointAt(double t) {
        return origin.add(direction.scale(t));
    }
}