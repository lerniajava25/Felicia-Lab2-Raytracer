package org.raytracer;

import org.raytracer.geometry.Sphere;
import org.raytracer.geometry.Triangle;

import org.raytracer.io.BmpWriter;
import org.raytracer.light.PointLight;
import org.raytracer.math.Vector3D;
import org.raytracer.math.Color;
import org.raytracer.render.Scene;

import java.io.IOException;

public class Main {
    static void main() {

        //Ljuskälla
        PointLight light = new PointLight(
                new Vector3D(6, 8, -2),
                new Color(255, 255, 255)
        );

        //Ljusgrå bakgrund
        Scene scene = new Scene(new Color(220, 220, 230), light);

        //Gul sphere
        scene.addShape(new Sphere(
                new Vector3D(1.5, -0.8, 4.5), 0.6,
                new Color(230, 200, 40)
        ));

        //Röd triangel
        scene.addShape(new Triangle(
                new Vector3D(-3, -1, 8), new Vector3D(-2, -1, 8), new Vector3D(-2.5, 0.8, 8),
                new Color(200, 40, 40)
        ));

        //Skapar bilden i bmp-format, eller ger ett felmeddelande
        Color[][] image = scene.render(600, 600);

        try {
            BmpWriter.writeBmp(image, "renderedImage.bmp");
            IO.println("renderedImage.bmp has been created.");
        } catch (IOException e) {
            IO.println("Failed to create image... " + e.getMessage());
        }
    }
}
