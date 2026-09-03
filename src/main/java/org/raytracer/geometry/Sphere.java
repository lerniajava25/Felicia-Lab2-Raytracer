package org.raytracer.geometry;

import org.raytracer.math.Ray;
import org.raytracer.math.Color;
import org.raytracer.math.Vector3D;

import java.util.Optional;

public class Sphere extends Shape {
    private final Vector3D center;
    private final double radius;

    public Sphere(Vector3D center, double radius, Color color) {
        super(color);
        this.center = center;
        this.radius = radius;
    }

    //Räknar ut matten för ray hit, utifrån formeln P = O + tD
    @Override
    public Optional<Intersection> hit(Ray ray) {
        Vector3D CO = ray.origin().subtract(center);
        double a = ray.direction().dot(ray.direction());
        double b = 2 * CO.dot(ray.direction());
        double c = CO.dot(CO) - radius * radius;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return Optional.empty();
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);
        double t1 = (-b - sqrtDiscriminant) / (2 * a);
        double t2 = (-b + sqrtDiscriminant) / (2 * a);

        double t;
        if (t1 > 0) {
            t = t1;
        } else if (t2 > 0) {
            t = t2;
        } else {
            return Optional.empty();
        }

        Vector3D point = ray.pointAt(t);
        Vector3D normal = point.subtract(center).normalize();

        return Optional.of(new Intersection(t, point, normal, color));
    }
    }
