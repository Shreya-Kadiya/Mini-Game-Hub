package WORDJUMBLE.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/*
 * OOP CONCEPT: INHERITANCE + POLYMORPHISM (UI BEHAVIOR OVERRIDE)
 * ---------------------------------------------------------------
 * - Extends JButton (Inheritance)
 * - Overrides paintComponent (Polymorphism)
 */

public class RoundedButton extends JButton {

    private Color normal, hover, pressed;
    private boolean isHover = false;
    private boolean isPressed = false;

    public RoundedButton(String text, Color n, Color h, Color p) {
        super(text);

        this.normal = n;
        this.hover = h;
        this.pressed = p;

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        setForeground(Color.WHITE);

        addMouseListener(new MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent e) {
                isHover = true;
                repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                isHover = false;
                isPressed = false;
                repaint();
            }

            public void mousePressed(java.awt.event.MouseEvent e) {
                isPressed = true;
                repaint();
            }

            public void mouseReleased(java.awt.event.MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color c = normal;
        if (isPressed) c = pressed;
        else if (isHover) c = hover;

        g2.setColor(c);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.setColor(Color.WHITE);
        g2.setFont(getFont());

        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 3;

        g2.drawString(getText(), x, y);
    }
}