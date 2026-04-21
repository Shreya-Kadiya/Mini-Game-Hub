package GuessTheWord.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class RoundedButton extends JButton {

    boolean hover = false;
    boolean pressed = false;

    //  Custom colors
    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;

    
    public RoundedButton(String text) {
        this(text, 
            new Color(70, 130, 180), 
            new Color(100, 160, 210), 
            new Color(50, 100, 150));
    }

   
    public RoundedButton(String text, Color normal, Color hover, Color pressed) {
        super(text);

        this.normalColor = normal;
        this.hoverColor = hover;
        this.pressedColor = pressed;

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);

        setForeground(Color.WHITE);// Text color
        setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
       

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RoundedButton.this.hover = true;
                repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                RoundedButton.this.hover = false;
                RoundedButton.this.pressed = false;
                repaint();
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                RoundedButton.this.pressed = true;
                repaint();
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                RoundedButton.this.pressed = false;
                repaint();
            }
        });
    }

    //  Painting the button
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int offset = pressed ? 3 : 0;

        //  Shadow (depth effect)
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillRoundRect(3, 5, width - 6, height - 3, 30, 30);

        // Button color based on state
        if (pressed) {
            g2.setColor(pressedColor);
        } else if (hover) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(normalColor);
        }
        g2.fillRoundRect(0, offset, width, height - offset, 30, 30);

        //  Border
        g2.setColor(new Color(255, 255, 255, 120));
        g2.drawRoundRect(0, offset, width - 1, height - offset - 1, 30, 30);

        // Draw centered text
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();

        g2.setColor(getForeground());
        g2.setFont(getFont());
        g2.drawString(
                getText(),
                (width - textWidth) / 2,
                (height + textHeight) / 2 - 3 + offset
        );

        g2.dispose();
    }
}
