package org.raytracer.geometry;

import org.raytracer.math.Vector3D;
import org.raytracer.math.Color;


public record Intersection(double t, Vector3D point, Color color) {
}
