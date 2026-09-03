package org.raytracer;

import org.raytracer.geometry.Intersection;
import org.raytracer.geometry.Shape;
import org.raytracer.math.Color;
import org.raytracer.math.Ray;
import org.raytracer.math.Vector3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Scene {
    private final List<Shape> shapes;
    private final Color backroundColor;

    public Scene(Color backroundColor) {
        this.shapes = new ArrayList<>();
        this.backroundColor = backroundColor;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    //Kollar om den prickar av något      
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
                int canvasY = canvasHeight / 2 - y; // flip y, since image rows go top-to-bottom

                Vector3D viewportPoint = canvasToViewport(canvasX, canvasY, canvasWidth, canvasHeight,
                        viewportWidth, viewportHeight, viewportDistance);
                Vector3D direction = viewportPoint.subtract(cameraOrigin).normalize();
                Ray ray = new Ray(cameraOrigin, direction);

                Optional<Intersection> result = traceRay(ray);
                image[x][y] = result.isPresent() ? result.get().color() : backroundColor;
            }
        }

        return image;
    }

}
