package org.raytracer.render;

import org.raytracer.geometry.Intersection;
import org.raytracer.geometry.Shape;
import org.raytracer.math.Color;
import org.raytracer.math.Ray;
import org.raytracer.math.Vector3D;

import org.raytracer.light.PointLight;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scene {
    private final List<Shape> shapes;
    private final Color backroundColor;
    private final PointLight light;

    public Scene(Color backroundColor, PointLight light) {
        this.shapes = new ArrayList<>();
        this.backroundColor = backroundColor;
        this.light = light;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    //Kolla om den prickar av något
    private Optional<Intersection> traceRay(Ray ray) {
        Intersection closest = null;

        for (Shape shape : shapes) {
            Optional<Intersection> result = shape.hit(ray);
            if (result.isPresent()) {
                Intersection hit = result.get();
                if (closest == null || hit.t() < closest.t()) {
                    closest = hit;
                }

            }
        }
        return Optional.ofNullable(closest);
    }

    //Räkna ut skuggan
    private boolean isInShadow(Vector3D point, Vector3D normal, PointLight light) {
        Vector3D shadowOrigin = point.add(normal.scale(0.001));
        Vector3D toLight = light.position().subtract(shadowOrigin);
        double distanceToLight = toLight.length();
        Vector3D shadowDirection = toLight.normalize();

        Ray shadowRay = new Ray(shadowOrigin, shadowDirection);
        Optional<Intersection> blocking = traceRay(shadowRay);

        return blocking.isPresent() && blocking.get().t() < distanceToLight;
    }

    private Vector3D canvasToViewport(int canvasX, int canvasY, int canvasWidth, int canvasHeight,
                                      double viewportWidth, double viewportHeight, double viewportDistance) {
        double x = canvasX * (viewportWidth / canvasWidth);
        double y = canvasY * (viewportHeight / canvasHeight);
        return new Vector3D(x, y, viewportDistance);
    }

    public Color[][] render(int canvasWidth, int canvasHeight) {
        Color[][] image = new Color[canvasWidth][canvasHeight];
        Vector3D cameraOrigin = new Vector3D(0, 0, 0);

        double viewportWidth = 1.0;
        double viewportHeight = 1.0;
        double viewportDistance = 1.0;

        for (int x = 0; x < canvasWidth; x++) {
            for (int y = 0; y < canvasHeight; y++) {

                // canvas till pixel
                int canvasX = x - canvasWidth / 2;
                int canvasY = canvasHeight / 2 - y; //Filppa bilden i y-led

                Vector3D viewportPoint = canvasToViewport(canvasX, canvasY, canvasWidth, canvasHeight,
                        viewportWidth, viewportHeight, viewportDistance);
                Vector3D direction = viewportPoint.subtract(cameraOrigin).normalize();
                Ray ray = new Ray(cameraOrigin, direction);

                Optional<Intersection> result = traceRay(ray);
                if (result.isPresent()) {
                    Intersection hit = result.get();
                    if (isInShadow(hit.point(), hit.normal(), light)) {
                        image[x][y] = hit.color().scale(0.2); //Inte helt svart färg
                    } else {
                        image[x][y] = hit.color().multiply(light.color());
                    }
                } else {
                    image[x][y] = backroundColor;
                }
            }
        }
        return image;
    }
}
