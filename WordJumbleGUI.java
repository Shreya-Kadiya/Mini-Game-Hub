import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class WordJumbleGUI {
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
    public WordJumbleGUI() {
        JFrame frame = new JFrame("Word Jumble");
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
        BufferedImage bgImage = getScaledImage("src\\WordJumble.png", 1024, 768);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setBounds(0, 0, 1024, 768); // Initial bounds
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        
        // Update background size when window is resized
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                BufferedImage scaledBg = getScaledImage("src\\WordJumble.png", frame.getWidth(), frame.getHeight());
                backgroundLabel.setIcon(new ImageIcon(scaledBg));
                backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            }
        });



        // Rules
         JTextArea rulesArea = new JTextArea(
            "                    Rules:\n\n" +
            "• A jumbled (shuffled) word will be shown\n" +
            "• Unscramble the letters to form a correct word\n" +
            "• Type your answer in the input box\n" +
            "• Click Submit to check your answer\n" +
            "• Each correct answer gives +1 point\n" +
            "• No negative marking\n" +
            "• Try to guess as many words as possible\n\n" +
            "   Think fast and arrange wisely!"
            );


        rulesArea.setEditable(false);
        rulesArea.setOpaque(false);
        rulesArea.setFont(new Font("Arial", Font.BOLD,  25));
        rulesArea.setForeground(new Color(1,27,59));

        rulesArea.setBounds(550, 300, 700, 400);
        layeredPane.add(rulesArea, JLayeredPane.PALETTE_LAYER); 

        //start button
        RoundedButton startButton = new RoundedButton("START");
        startButton.setBounds(650, 650, 150, 50);
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
        new WordJumbleGUI();
}
}