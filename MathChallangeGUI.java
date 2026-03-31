import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MathChallangeGUI {
    public BufferedImage getScaledImage(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = result.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public MathChallangeGUI() {
        JFrame frame = new JFrame("Math challange");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //back button
       RoundedButton backButton = new RoundedButton("Back");
       backButton.setBounds(20, 20, 100, 40);
         backButton.addActionListener(e -> {
              frame.dispose(); // Close Sudoku window
              new Dashboard(); // Open Dashboard
    });
        // Create JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Use absolute positioning
        layeredPane.setBackground(new Color(20, 20, 20));

        // Add background image as a label (bottom layer)
        BufferedImage bgImage = getScaledImage("src\\MathSolve.png", 1024, 768);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setBounds(0, 0, 1024, 768); // Initial bounds
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        
        // Update background size when window is resized
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                BufferedImage scaledBg = getScaledImage("src\\MathSolve.png", frame.getWidth(), frame.getHeight());
                backgroundLabel.setIcon(new ImageIcon(scaledBg));
                backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            }
        });


        // Rules
         JTextArea rulesArea = new JTextArea(
            "                          Rules\n\n" +
            "• You have 60 seconds to solve questions\n" +
            "• Answer as many questions as you can\n" +
            "• Each correct answer gives +1 point\n" +
            "• No negative marking\n" +
            "• Enter your answer and click Submit\n" +
            "• Next question appears instantly\n" +
            "• Game ends when time is over\n\n" +
            "    Try to score as high as possible!"
        );


        rulesArea.setEditable(false);
        rulesArea.setOpaque(false);
        rulesArea.setFont(new Font("Arial", Font.BOLD,  25));
        rulesArea.setForeground(new Color(73,110,6));

        rulesArea.setBounds(550, 300, 700, 400);
        layeredPane.add(rulesArea, JLayeredPane.PALETTE_LAYER); 

        //start button
        RoundedButton startButton = new RoundedButton("START");
        startButton.setBounds(700, 650, 150, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.addActionListener(e -> {
            // Start game logic here
        });
            


        frame.add(backButton);
        frame.add(startButton);
        frame.add(layeredPane);
        frame.setVisible(true);
       
    }

    public static void main(String[] args) {
        new MathChallangeGUI();
}
}