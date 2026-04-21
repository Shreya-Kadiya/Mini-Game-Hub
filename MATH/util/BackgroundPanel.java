package MATH.util;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/*
 * UTIL CLASS (OOP - REUSABLE COMPONENT)
 * -------------------------------------
 * RESPONSIBILITY:
 * - Loads and renders background image
 * - Used by ALL screens (GUI reuse = OOP benefit)
 *
 * CONCEPT USED:
 * ✔ Encapsulation → image is private
 * ✔ Reusability → used across multiple screens
 */



public class BackgroundPanel extends JPanel {

    private BufferedImage image;

    public BackgroundPanel(String fileName) {

        try {
            // SIMPLE + RELIABLE (NO user.dir confusion)
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("❌ File not found: " + file.getAbsolutePath());
            }

            image = ImageIO.read(file);

        } catch (Exception e) {
            System.out.println("❌ Error loading image");
            e.printStackTrace();
        }

        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.out.println("⚠ Image is null (not loaded)");
        }
    }
}