package WORDJUMBLE.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/*
 * OOP CONCEPT: ENCAPSULATION + REUSABILITY
 * ---------------------------------------
 * - Encapsulates image loading logic
 * - Reusable across all screens
 */

public class BackgroundPanel extends JPanel {

    private BufferedImage image; // ENCAPSULATED

    public BackgroundPanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            System.out.println("Background image not found: " + path);
        }
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}