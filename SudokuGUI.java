import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SudokuGUI {
    private BufferedImage getScaledImage(String path, int width, int height) {
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
    public SudokuGUI() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Use absolute positioning
        layeredPane.setBackground(new Color(20, 20, 20));

        // Add background image as a label (bottom layer)
        BufferedImage bgImage = getScaledImage("src\\Sudoku.png", 1024, 768);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setBounds(0, 0, 1024, 768); // Initial bounds
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
        
        // Update background size when window is resized
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                BufferedImage scaledBg = getScaledImage("src\\Sudoku.png", frame.getWidth(), frame.getHeight());
                backgroundLabel.setIcon(new ImageIcon(scaledBg));
                backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            }
        });


        // Add your gridPanel on top (higher layer)
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4));
        gridPanel.setBounds(500, 170, 500, 500);
        gridPanel.setOpaque(true);
        gridPanel.setBackground(new Color(250, 250, 250));
        gridPanel.setBorder(BorderFactory.createLineBorder(new Color(18, 4, 85), 4));
       
        JTextField[][] cells = new JTextField[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final int row = i;
                final int col = j;
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setOpaque(true);
                cell.setBackground(new Color(251,236,253));//color of cell background
                cell.setFont(new Font("Arial", Font.BOLD, 50));
                cell.setBorder(BorderFactory.createLineBorder(new Color(36,24,93), 2));//color of cell border
                if ((i / 2 + j / 2) % 2 == 0) {
                    cell.setBackground(new Color(249,199,229)); // light variation
                } 
                else {
                    cell.setBackground(new Color(244,129,199)); // dark variation
                }


                cell.addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusGained(java.awt.event.FocusEvent e) {
                        cell.setBackground(new Color(238,27,116)); // highlight
                    }

                    public void focusLost(java.awt.event.FocusEvent e) {
                        if ((row/ 2 + col/ 2) % 2 == 0) {
                            cell.setBackground(new Color(249,199,229));
                        } else {
                            cell.setBackground(new Color(244,129,199));
                        }
                    }
                }
            );
                
                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }

      
       layeredPane.add(gridPanel, JLayeredPane.PALETTE_LAYER); // Higher layer 
       frame.setContentPane(layeredPane);

        frame.setVisible(true);
    }



    public static void main(String[] args) {
        new SudokuGUI();
    }
}