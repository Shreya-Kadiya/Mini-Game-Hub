
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SudokuGUI {
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
    public SudokuGUI() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //back button
       RoundedButton backButton = new RoundedButton("Back");
       backButton.setBounds(20, 20, 100, 40);
         backButton.addActionListener(e -> {
              frame.dispose(); // Close Sudoku window
              new Dashboard(); // Open Dashboard
    });
        // Create JLayeredPa
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


        
        JTextArea rulesArea = new JTextArea(
            "                    Rules:\n\n" +
            "• Fill the 4x4 grid with numbers 1 to 4\n" +
            "• Each number must appear only once \n in every row\n" +
            "• Each number must appear only once \n in every column\n" +
            "• Each 2x2 box must contain numbers \n 1 to 4 without repetition\n" +
            "• Do not change the pre-filled numbers\n" +
            "• Complete the grid correctly to win\n\n" +
            "       Use logic, not guessing!"
        );

        rulesArea.setEditable(false);
        rulesArea.setOpaque(false);
        rulesArea.setFont(new Font("Arial BOLD", Font.PLAIN, 20));
        rulesArea.setForeground(new Color(128,11,63));
        rulesArea.setBounds(1050, 200, 750, 800);
        layeredPane.add(rulesArea, JLayeredPane.PALETTE_LAYER);
        

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



        //check button
        RoundedButton checkButton = new RoundedButton("CHECK");
        checkButton.setBounds(850, 700, 150, 40);
         checkButton.addActionListener(e -> {
            //check logic
         });
         //reset button
        RoundedButton resetButton = new RoundedButton("RESET");
        resetButton.setBounds(500, 700, 150, 40);
         resetButton.addActionListener(e -> {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    cells[i][j].setText("");
                }
            }
            });



      
        layeredPane.add(gridPanel, JLayeredPane.PALETTE_LAYER); // Higher layer 
        frame.setContentPane(layeredPane);
        frame.setLayout(null);
        layeredPane.add(backButton, JLayeredPane.MODAL_LAYER); // Top layer for back button
        layeredPane.add(checkButton, JLayeredPane.MODAL_LAYER); // Top layer for check button
        layeredPane.add(resetButton, JLayeredPane.MODAL_LAYER); // Top layer for reset button
        frame.setVisible(true);
    }
    



    public static void main(String[] args) {
        new SudokuGUI();
    }
}
