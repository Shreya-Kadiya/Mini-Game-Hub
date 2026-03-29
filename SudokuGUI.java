import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            System.out.println("Error loading background: " + e.getMessage());
        }
        setLayout(null);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}



public class SudokuGUI {
    public SudokuGUI() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        BackgroundPanel background = new BackgroundPanel("src\\Sudoku.png");
        frame.setContentPane(background);

        JButton backBtn = new JButton("← Back");
        backBtn.setBounds(20, 20, 100, 40); // top-left position

        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("Arial bold", Font.BOLD, 16));
        backBtn.setBackground(new Color(210,221,250));//back button background color
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(6,31,95), 4));//back button border
        backBtn.addActionListener(e -> {
            frame.dispose(); // close Sudoku window
            new Dashboard();// open your dashboard
            });

        background.add(backBtn);

        // 🎯 Sudoku Grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4));
        gridPanel.setBounds(500, 170, 500, 500);
        gridPanel.setOpaque(true);
        gridPanel.setBackground(new Color(250,250,250));// 
        gridPanel.setBorder(BorderFactory.createLineBorder(new Color(18,4,85),4));
       
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

      

        background.add(gridPanel);

        frame.setVisible(true);
    }



    public static void main(String[] args) {
        new SudokuGUI();
    }
}
        

        // frame.setSize(500, 500);
        // frame.setLocationRelativeTo(null);
        // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // JLabel label = new JLabel("Sudoku Game Screen", JLabel.CENTER);
        // label.setFont(new Font("Arial", Font.BOLD, 20));
        // frame.add(label);
    