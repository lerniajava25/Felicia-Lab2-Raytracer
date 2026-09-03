package org.raytracer;

import org.raytracer.geometry.Sphere;
import org.raytracer.geometry.Triangle;

import org.raytracer.math.Vector3D;
import org.raytracer.math.Color;


import java.io.IOException;

public class Main {
    static void main() {
        //Bakgrund med vit bakgrundsfärg
        Scene scene = new Scene(new Color (255, 255, 255));

        //Lila "Sphere"
        scene.addShape(new Sphere(
                new Vector3D(0, 0, 5), 1,
                new Color(100, 0, 100)
        ));

        //Röd triangel
        scene.addShape(new Triangle(
                new Vector3D(-2, -1, 6),
                new Vector3D(-1, -1, 6),
                new Vector3D(-1.5, 0.5, 6),
                new Color(255, 0, 0)
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
