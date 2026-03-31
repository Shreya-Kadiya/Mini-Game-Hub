

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


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

        // Shadow
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, cornerRadius, cornerRadius);

        // Glass background
        g2.setColor(new Color(255, 255, 255, 40));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Border
        g2.setColor(new Color(255, 255, 255, 120));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
    }
}

// Button with Hover + Press Effect
class RoundedButton extends JButton {

    private boolean hover = false;
    private boolean pressed = false;

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(new Color(245, 245, 245));
        setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));

        addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hover = true;
                repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                hover = false;
                pressed = false;
                repaint();
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                pressed = true;
                repaint();
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pressed = false;
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int offset = 0;

        // 🎯 States
        if (pressed) {
            g2.setColor(new Color(48,14,186)); // pressed
            offset = 2;
        } else if (hover) {
            g2.setColor(new Color(26,3,85)); // hover
        } else {
            g2.setColor(new Color(5,72,149)); // normal
        }

        g2.fillRoundRect(0, offset, getWidth(), getHeight() - offset, 30, 30);

        g2.translate(0, offset);
        super.paintComponent(g);
        g2.translate(0, -offset);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(230, 230, 250));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
    }
}

// Main Dashboard
public class Dashboard {

    //Background Image Scaling
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
    
    public Dashboard() {
        JFrame frame = new JFrame("Mini Game Hub");
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
                BufferedImage scaledBg = getScaledImage("src\\bgimg2.png", frame.getWidth(), frame.getHeight());
                backgroundLabel.setIcon(new ImageIcon(scaledBg));
                backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            }
        });
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 4, 20, 40));
        centerPanel.setBounds(340, 250, 850, 300);
        centerPanel.setOpaque(false);
        layeredPane.add(centerPanel, JLayeredPane.PALETTE_LAYER);

        String[] games = {"Sudoku", "Word Jumble", "Math Challenge", "Word Decode"};

        for (int i = 0; i < 4; i++) {
            final int index = i;
            RoundedPanel card = new RoundedPanel();
            card.setLayout(new BorderLayout(10, 10));

            JLabel title = new JLabel(games[i], JLabel.CENTER);
            title.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 22));
            title.setForeground(new Color(18, 40, 90)); // deep navy

            ImageIcon originalIcon = new ImageIcon("src\\game" + (i + 1) + ".jpg");
            Image scaledImage = originalIcon.getImage().getScaledInstance(160, 140, Image.SCALE_SMOOTH);
            JLabel image = new JLabel(new ImageIcon(scaledImage));
            image.setHorizontalAlignment(JLabel.CENTER);

            RoundedButton playBtn = new RoundedButton("PLAY");

            playBtn.addActionListener(e -> {
                frame.dispose(); // Close dashboard 
                switch (index) {
                    case 0:
                        new SudokuGUI();
                        break;
                    case 1:
                        new WordJumbleGUI();
                        break;
                    case 2:
                        new MathChallngeGUI();
                        break;
                    case 3:
                        new WordDecodeGUI();
                        break;
                }
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
        frame.setContentPane(layeredPane);
        
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Dashboard();
    }
}