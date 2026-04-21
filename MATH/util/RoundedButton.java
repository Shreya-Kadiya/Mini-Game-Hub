package MATH.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/*
 * Custom UI component
 */

public class RoundedButton extends JButton {

    private boolean hover = false;
    private boolean pressed = false;

    private Color normal;
    private Color hoverC;
    private Color pressC;

    public RoundedButton(String text, Color n, Color h, Color p) {
        super(text);

        normal = n;
        hoverC = h;
        pressC = p;

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 18));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hover = true; repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                hover = false; pressed = false; repaint();
            }
            public void mousePressed(java.awt.event.MouseEvent e) {
                pressed = true; repaint();
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
                pressed = false; repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (pressed) g2.setColor(pressC);
        else if (hover) g2.setColor(hoverC);
        else g2.setColor(normal);

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.setColor(Color.WHITE);
        g2.drawString(getText(), getWidth()/4, getHeight()/2 + 5);
    }
}