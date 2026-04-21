import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

// //  Background Panel 
// class BackgroundPanel extends JPanel {
//     private BufferedImage backgroundImage;

//     public BackgroundPanel(String path) {
//         try {
//             backgroundImage = ImageIO.read(new File(path));
//         } catch (Exception e) {
//             System.out.println("Background image not found!");
//         }
//         setLayout(null); 
//     }

//     protected void paintComponent(Graphics g) {
        
//         if (backgroundImage != null) {
//             g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//         }
//     }
// }


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


// // Custom Button
// class RoundedButton extends JButton {

//     boolean hover = false;
//     boolean pressed = false;


//     //  Custom colors
//     private Color normalColor;
//     private Color hoverColor;
//     private Color pressedColor;

    
//     public RoundedButton(String text) {
//         this(text, 
//             new Color(70, 130, 180), 
//             new Color(100, 160, 210), 
//             new Color(50, 100, 150));
//     }

   
//     public RoundedButton(String text, Color normal, Color hover, Color pressed) {
//         super(text);

//         this.normalColor = normal;
//         this.hoverColor = hover;
//         this.pressedColor = pressed;

//         setFocusPainted(false);
//         setContentAreaFilled(false);
//         setBorderPainted(false);
//         setOpaque(false);

//         setForeground(Color.WHITE);// Text color
//         setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
       

//         addMouseListener(new java.awt.event.MouseAdapter() {
//             public void mouseEntered(java.awt.event.MouseEvent evt) {
//                 RoundedButton.this.hover = true;
//                 repaint();
//             }

//             public void mouseExited(java.awt.event.MouseEvent evt) {
//                 RoundedButton.this.hover = false;
//                 RoundedButton.this.pressed = false;
//                 repaint();
//             }

//             public void mousePressed(java.awt.event.MouseEvent evt) {
//                 RoundedButton.this.pressed = true;
//                 repaint();
//             }

//             public void mouseReleased(java.awt.event.MouseEvent evt) {
//                 RoundedButton.this.pressed = false;
//                 repaint();
//             }
//         });
//     }

//     //  Painting the button
//        protected void paintComponent(Graphics g) {
//         Graphics2D g2 = (Graphics2D) g.create();

//         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                 RenderingHints.VALUE_ANTIALIAS_ON);

//         int width = getWidth();
//         int height = getHeight();

//         int offset = pressed ? 3 : 0;

//         //  Shadow (depth effect)
//         g2.setColor(new Color(0, 0, 0, 80));
//         g2.fillRoundRect(3, 5, width - 6, height - 3, 30, 30);

//         // Button color based on state
//         if (pressed) {
//             g2.setColor(pressedColor);
//         } else if (hover) {
//             g2.setColor(hoverColor);
//         } else {
//             g2.setColor(normalColor);
//         }
//         g2.fillRoundRect(0, offset, width, height - offset, 30, 30);

//         //  Border
//         g2.setColor(new Color(255, 255, 255, 120));
//         g2.drawRoundRect(0, offset, width - 1, height - offset - 1, 30, 30);

//         // Draw centered text
//         FontMetrics fm = g2.getFontMetrics();
//         int textWidth = fm.stringWidth(getText());
//         int textHeight = fm.getAscent();

//         g2.setColor(getForeground());
//         g2.setFont(getFont());
//         g2.drawString(
//                 getText(),
//                 (width - textWidth) / 2,
//                 (height + textHeight) / 2 - 3 + offset
//         );

//         g2.dispose();
//     }
// }



public class Dashboard {

    public Dashboard() {
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
                    case 0: new SudokuGUI(); break;
                    case 1: new WordJumbleGUI(); break;
                    case 2: new MATH.MathChallangeGUI(); break;
                    case 3: new WordGuessGUI(); break;
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
        new Dashboard();
    }
}