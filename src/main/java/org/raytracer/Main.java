package org.raytracer;

import org.raytracer.geometry.Intersection;
import org.raytracer.geometry.Sphere;
import org.raytracer.math.Ray;
import org.raytracer.math.Vector3D;
import org.raytracer.math.Color;


import java.util.Optional;

public class Main {
    static void main() {

        //  Test för sphere
        Sphere sphere = new Sphere(
                new Vector3D(0, 0, 5),
                5.0,
                new Color(0, 0, 255)
        );

        // "Origin" för ray
        Ray ray = new Ray(
                new Vector3D(0, 0, 0),
                new Vector3D(0, 0, 1)
        );

        // Kollar om den prickar av sphere
        Optional<Intersection> result = sphere.hit(ray);

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
