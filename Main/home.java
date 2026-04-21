package Main;

import javax.swing.*;
import java.awt.*;

import GuessTheWord.*;
import WORDJUMBLE.ui.*;
import Sudoku_4_4.*;
import MATH.*;
import GuessTheWord.util.BackgroundPanel;
import GuessTheWord.util.RoundedButton;




// Card Panel

class RoundedPanel extends JPanel {
    private int cornerRadius = 40;

    public RoundedPanel() {
        setOpaque(false);
        setBorder(new javax.swing.border.EmptyBorder(15, 5, 5, 10));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Shadow effect
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, cornerRadius, cornerRadius);
        // Panel background
        g2.setColor(new Color(255, 255, 255, 40));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        // Border
        g2.setColor(new Color(255, 255, 255, 120));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
    }
}

public class home
 {

    public home() {
        JFrame frame = new JFrame("Mini Game Hub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //  Use BackgroundPanel 
        BackgroundPanel panel = new BackgroundPanel("images/bgimg2.png");
        frame.setContentPane(panel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 4, 20, 40));
        centerPanel.setBounds(340, 250, 850, 300);
        centerPanel.setOpaque(false);
        panel.add(centerPanel);

        String[] games = {"Sudoku", "Word Jumble", "Math Challenge", "Guess the Word"};

        for (int i = 0; i < 4; i++) {
            final int index = i;

            RoundedPanel card = new RoundedPanel();
            card.setLayout(new BorderLayout(10, 10));

            JLabel title = new JLabel(games[i], JLabel.CENTER);
            title.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 22));
            title.setForeground(new Color(18, 40, 90));

            ImageIcon originalIcon = new ImageIcon("images/game" + (i + 1) + ".jpg");
            Image scaledImage = originalIcon.getImage().getScaledInstance(160, 140, Image.SCALE_SMOOTH);
            JLabel image = new JLabel(new ImageIcon(scaledImage));
            image.setHorizontalAlignment(JLabel.CENTER);

            RoundedButton playBtn = new RoundedButton("PLAY",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
            );

            playBtn.addActionListener(e -> {
                
                switch (index) {
                    case 0 -> new SudokuGUI();
                    case 1 -> new WordJumbleGUI();
                    case 2 -> new MathChallangeGUI();
                    case 3 -> new WordGuessGUI();
                }
                frame.dispose();
            });

            playBtn.setPreferredSize(new Dimension(100, 40));

            JPanel btnPanel = new JPanel();
            btnPanel.setOpaque(false);
            btnPanel.add(playBtn);

            card.add(title, BorderLayout.NORTH);
            card.add(image, BorderLayout.CENTER);
            card.add(btnPanel, BorderLayout.SOUTH);

            centerPanel.add(card);
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new home();
    }
}