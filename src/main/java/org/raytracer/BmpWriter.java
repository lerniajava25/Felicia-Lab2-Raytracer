package org.raytracer;

import org.raytracer.math.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BmpWriter  {
    public static void writeBmp(Color[][] image, String filename)
            throws IOException {
        int width = image.length;
        int height = image[0].length;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        //Gör om färgerna till int:s så att de funkar med BufferedImage
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = image[x][y];
                int r = (int) color.r();
                int g = (int) color.g();
                int b = (int) color.b();

                int rgb = (r << 16) | (g << 8) | b;
                bufferedImage.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(bufferedImage, "bmp", new File(filename));

    }
}
