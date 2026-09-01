package org.raytracer.geometry;

import org.raytracer.math.Ray;
import org.raytracer.math.Vector3D;
import org.raytracer.math.Color;

import java.util.Optional;

public class Triangle extends Shape {
    private final Vector3D v0, v1, v2;
    private static final double EPSILON = 1e-8;

    public Triangle(Vector3D v0, Vector3D v1, Vector3D v2, Color color) {
        super(color);
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
    }

    //Räknar ut matten för ray hit, utifrån Möller-Trumbore-algoritmen
    @Override
    public Optional<Intersection> hit(Ray ray) {
        Vector3D edge1 = v1.subtract(v0);
        Vector3D edge2 = v2.subtract(v0);

        Vector3D h = ray.direction().cross(edge2);
        double a = edge1.dot(h);

        if (Math.abs(a) < EPSILON) {
            return Optional.empty();
        }

        double f = 1.0 / a;
        Vector3D s = ray.origin().subtract(v0);
        double u = f * s.dot(h);

        if (u < 0 || u > 1) {
            return Optional.empty();
        }

        Vector3D q = s.cross(edge1);
        double v = f * ray.direction().dot(q);

        if (v < 0 || u + v > 1) {
            return Optional.empty();
        }

        double t = f * edge2.dot(q);

        if (t > EPSILON) {
            Vector3D point = ray.pointAt(t);
            return Optional.of(new Intersection(t, point, color));
        } else {
            return Optional.empty();
        }
    }
}
