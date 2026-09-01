package org.raytracer;

import org.raytracer.geometry.Intersection;
import org.raytracer.geometry.Sphere;
import org.raytracer.geometry.Triangle;
import org.raytracer.math.Ray;
import org.raytracer.math.Vector3D;
import org.raytracer.math.Color;


import java.util.Optional;

public class Main {
    static void main() {

        //  Test för triangel
        Triangle triangle = new Triangle(
                new Vector3D(-1, -1, 5),
                new Vector3D(1, -1, 5),
                new Vector3D(0, 1, 5),
                new Color(0, 0, 255)
        );

        // "Origin" för ray
        Ray ray = new Ray(
                new Vector3D(0, 0, 0),
                new Vector3D(0, 0, 1)
        );

        // Kollar om den prickar av triangel
        Optional<Intersection> result = triangle.hit(ray);

        if (result.isPresent()) {
            Intersection hit = result.get();
            IO.println("Ray hit: t = " + hit.t());
            IO.println("Point: " + hit.point());
            IO.println("Color: " + hit.color());
        } else {
            IO.println("No hit.");
        }
    }
}
